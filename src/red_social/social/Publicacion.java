/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: Publicacion
 * AUTOR: [jaun silva]
 * FECHA: 2025
 */

package red_social.social;

import red_social.usuarios.Perfil;
import red_social.proyectos.Avance;
import red_social.proyectos.Proyecto;
import java.util.ArrayList;

/*
 * Publicacion en el feed social.
 * Codigo unico autogenerado en base-26: PUB-A, PUB-B, PUB-AA ...
 * Like simplificado: un usuario solo puede dar un like por publicacion.
 */
public class Publicacion implements AccionesSociales {

    // Contador estatico para generar codigos base-26
    private static int contadorGlobal = 0;

    private String codigo;              // PUB-A, PUB-B, PUB-AA ...
    private Perfil autor;
    private String contenido;
    private String fecha;
    private String area;
    private ArrayList<Comentario>   comentarios;
    private ArrayList<String>       likesCorreos; // correos de quienes dieron like
    private ArrayList<Proyecto>     suscripciones;

    // Constructor vacio
    public Publicacion() {
        this.codigo       = generarCodigo();
        this.autor        = null;
        this.contenido    = "";
        this.fecha        = "";
        this.area         = "";
        this.comentarios  = new ArrayList<>();
        this.likesCorreos = new ArrayList<>();
        this.suscripciones= new ArrayList<>();
    }

    // Constructor completo
    public Publicacion(Perfil autor, String contenido, String fecha, String area) {
        this.codigo       = generarCodigo();
        this.autor        = autor;
        this.contenido    = contenido;
        this.fecha        = fecha;
        this.area         = area;
        this.comentarios  = new ArrayList<>();
        this.likesCorreos = new ArrayList<>();
        this.suscripciones= new ArrayList<>();
    }

    // Constructor para cargar desde archivo (codigo ya conocido)
    public Publicacion(String codigo, Perfil autor, String contenido,
                       String fecha, String area) {
        this.codigo       = codigo;
        this.autor        = autor;
        this.contenido    = contenido;
        this.fecha        = fecha;
        this.area         = area;
        this.comentarios  = new ArrayList<>();
        this.likesCorreos = new ArrayList<>();
        this.suscripciones= new ArrayList<>();
    }

    // ── Generador codigo base-26 ──────────────────────────────────

    private static String generarCodigo() {
        contadorGlobal++;
        return "PUB-" + aBase26(contadorGlobal);
    }

    public static void setContadorGlobal(int valor) {
        contadorGlobal = valor;
    }

    public static int getContadorGlobal() { return contadorGlobal; }

    static String aBase26(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            n--;
            sb.insert(0, (char)('A' + (n % 26)));
            n /= 26;
        }
        return sb.toString();
    }

    // ── Getters ───────────────────────────────────────────────────
    public String getCodigo()                     { return this.codigo; }
    public Perfil getAutor()                      { return this.autor; }
    public String getContenido()                  { return this.contenido; }
    public String getFecha()                      { return this.fecha; }
    public String getArea()                       { return this.area; }
    public ArrayList<Comentario> getComentarios() { return this.comentarios; }
    public int getCantidadComentarios() {
        return this.comentarios.size();
    }
    public ArrayList<String> getLikesCorreos()    { return this.likesCorreos; }
    public int getCantidadLikes()                 { return this.likesCorreos.size(); }

    // ── Setters ───────────────────────────────────────────────────
    public void setAutor(Perfil autor)       { this.autor    = autor; }
    public void setContenido(String c)       { this.contenido= c; }
    public void setFecha(String fecha)       { this.fecha    = fecha; }
    public void setArea(String area)         { this.area     = area; }

    // ── Like simple ───────────────────────────────────────────────

    public void darLike(Perfil usuario) {
        String correo = usuario.getCorreo();
        if (!this.likesCorreos.contains(correo)) {
            this.likesCorreos.add(correo);
        }
    }

    public void quitarLike(Perfil usuario) {
        this.likesCorreos.remove(usuario.getCorreo());
    }

    public boolean tienelike(Perfil usuario) {
        return this.likesCorreos.contains(usuario.getCorreo());
    }

    // ── Comentarios ───────────────────────────────────────────────

    public void agregarComentario(Comentario c) {
        this.comentarios.add(c);
    }

    // ── AccionesSociales ──────────────────────────────────────────

    @Override
    public void publicarAvance(Avance avance) {
        this.contenido = "Avance v" + avance.getVersion() + ": " + avance.getDescripcion();
    }

    @Override
    public void comentar(String texto) {
        System.out.println("Usa agregarComentario(Comentario) para comentar.");
    }

    @Override
    public void reaccionar(String tipo) {
        System.out.println("Usa darLike(Perfil) para reaccionar.");
    }

    @Override
    public void suscribirse(Proyecto proyecto) {
        this.suscripciones.add(proyecto);
    }

    // ── Persistencia ──────────────────────────────────────────────

    /*
     * Formato: codigo;correoAutor;contenido;fecha;area;likes(c1,c2)
     * El contenido NO puede tener ';' — se reemplaza por [SC]
     */
    public String aTexto() {
        String cSafe   = this.contenido.replace(";", "[SC]").replace("\n", "[NL]");
        String likesStr= String.join(",", this.likesCorreos);
        return this.codigo + ";" +
               (this.autor != null ? this.autor.getCorreo() : "") + ";" +
               cSafe + ";" +
               this.fecha + ";" +
               this.area  + ";" +
               likesStr;
    }

    /*
     * Solo reconstruye los campos simples (codigo, correoAutor, contenido, fecha, area, likes).
     * El objeto Perfil se debe resolver en PersistenciaSocial buscando por correo en GestorUsuarios.
     */
    public static Publicacion desdeTexto(String linea) {
        String[] p = linea.split(";", -1);
        if (p.length < 5) return null;
        String codigo    = p[0];
        // p[1] = correoAutor → se resuelve fuera
        String contenido = p[2].replace("[SC]", ";").replace("[NL]", "\n");
        String fecha     = p[3];
        String area      = p[4];
        Publicacion pub  = new Publicacion(codigo, null, contenido, fecha, area);
        if (p.length > 5 && !p[5].isEmpty()) {
            for (String correo : p[5].split(",")) {
                pub.getLikesCorreos().add(correo);
            }
        }
        return pub;
    }

    // Devuelve el correo del autor para reconstruir la referencia desde archivo
    public String getCorreoAutorTexto(String linea) {
        String[] p = linea.split(";", -1);
        return p.length > 1 ? p[1] : "";
    }
}
