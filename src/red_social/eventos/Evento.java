/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: Evento
 * AUTOR: [jaun silva]
 * FECHA: 2025
 */

package red_social.eventos;

import red_social.proyectos.Proyecto;

// Evento academico: entrega, presentacion, mentoria, reunion
public class Evento {

    // Contador estatico para codigos base-26
    private static int contadorGlobal = 0;

    private String codigo;       // EVT-A, EVT-B, EVT-AA ...
    private String nombre;
    private String fecha;
    private String tipo;         // ENTREGA, PRESENTACION, MENTORIA, REUNION
    private String descripcion;
    private String correoCreador;
    private Proyecto proyecto;   // puede ser null

    public Evento() {
        this.codigo        = generarCodigo();
        this.nombre        = "";
        this.fecha         = "";
        this.tipo          = "";
        this.descripcion   = "";
        this.correoCreador = "";
        this.proyecto      = null;
    }

    public Evento(String nombre, String fecha, String tipo,
                  String descripcion, String correoCreador, Proyecto proyecto) {
        this.codigo        = generarCodigo();
        this.nombre        = nombre;
        this.fecha         = fecha;
        this.tipo          = tipo;
        this.descripcion   = descripcion;
        this.correoCreador = correoCreador;
        this.proyecto      = proyecto;
    }

    // Constructor para cargar desde archivo (codigo ya conocido)
    public Evento(String codigo, String nombre, String fecha, String tipo,
                  String descripcion, String correoCreador) {
        this.codigo        = codigo;
        this.nombre        = nombre;
        this.fecha         = fecha;
        this.tipo          = tipo;
        this.descripcion   = descripcion;
        this.correoCreador = correoCreador;
        this.proyecto      = null;
    }

    // ── Generador codigo base-26 ──────────────────────────────────

    private static String generarCodigo() {
        contadorGlobal++;
        return "EVT-" + aBase26(contadorGlobal);
    }

    public static void setContadorGlobal(int valor) { contadorGlobal = valor; }
    public static int  getContadorGlobal()           { return contadorGlobal; }

    static String aBase26(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) { n--; sb.insert(0, (char)('A' + (n % 26))); n /= 26; }
        return sb.toString();
    }

    // ── Getters ───────────────────────────────────────────────────
    public String getCodigo()        { return this.codigo; }
    public String getNombre()        { return this.nombre; }
    public String getFecha()         { return this.fecha; }
    public String getTipo()          { return this.tipo; }
    public String getDescripcion()   { return this.descripcion; }
    public String getCorreoCreador() { return this.correoCreador; }
    public Proyecto getProyecto()    { return this.proyecto; }

    // ── Setters ───────────────────────────────────────────────────
    public void setNombre(String nombre)             { this.nombre      = nombre; }
    public void setFecha(String fecha)               { this.fecha       = fecha; }
    public void setTipo(String tipo)                 { this.tipo        = tipo; }
    public void setDescripcion(String descripcion)   { this.descripcion = descripcion; }
    public void setCorreoCreador(String correo)      { this.correoCreador = correo; }
    public void setProyecto(Proyecto proyecto)       { this.proyecto    = proyecto; }

    public void mostrarEvento() {
        System.out.println("  [" + this.codigo + "] " + this.nombre);
        System.out.println("  Tipo        : " + this.tipo);
        System.out.println("  Fecha       : " + this.fecha);
        System.out.println("  Descripcion : " + this.descripcion);
        if (this.proyecto != null)
            System.out.println("  Proyecto    : " + this.proyecto.getNombre());
    }

    // Formato: codigo;nombre;fecha;tipo;descripcion;correoCreador;idProyecto
    public String aTexto() {
        String dSafe = this.descripcion.replace(";", "[SC]");
        String pid   = (this.proyecto != null) ? this.proyecto.getId() : "";
        return this.codigo + ";" + this.nombre + ";" + this.fecha + ";" +
               this.tipo  + ";" + dSafe + ";" + this.correoCreador + ";" + pid;
    }

    public static Evento desdeTexto(String linea) {
        String[] p = linea.split(";", -1);
        if (p.length < 6) return null;
        String desc = p[4].replace("[SC]", ";");
        return new Evento(p[0], p[1], p[2], p[3], desc, p[5]);
        // p[6] = idProyecto → se resuelve fuera si es necesario
    }

	
}
