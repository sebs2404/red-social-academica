/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Persistencia
 * CLASE: PersistenciaSocial
 * AUTOR: [Tu nombre]
 * FECHA: 2025
 */

package red_social.persistencia;

import red_social.social.Comentario;
import red_social.social.Feed;
import red_social.social.Publicacion;
import red_social.usuarios.GestorUsuarios;
import red_social.usuarios.Perfil;

import java.util.ArrayList;

/*
 * Hereda de Persistencia (igual que PersistenciaUsuarios).
 * Guarda y carga publicaciones y comentarios del feed.
 */
public class PersistenciaSocial extends Persistencia {

    private Persistencia persComentarios;

    public PersistenciaSocial() {
        super("datos/publicaciones.txt");
        this.persComentarios = new Persistencia("datos/comentarios.txt");
    }

    // ── Publicaciones ─────────────────────────────────────────────

    public void guardarFeed(Feed feed) {
        ArrayList<String> lineas = new ArrayList<>();
        for (Publicacion p : feed.getPublicaciones()) {
            lineas.add(p.aTexto());
        }
        guardar(lineas);

        // Guardar comentarios de todas las publicaciones
        ArrayList<String> lineasComentarios = new ArrayList<>();
        for (Publicacion p : feed.getPublicaciones()) {
            for (Comentario c : p.getComentarios()) {
                lineasComentarios.add(c.aTexto(p.getCodigo()));
            }
        }
        persComentarios.guardar(lineasComentarios);
    }

    /*
     * Carga el feed y resuelve las referencias a Perfil usando GestorUsuarios.
     * Si un autor no existe en el sistema, la publicacion se carga igual (autor null).
     */
    public Feed cargarFeed(GestorUsuarios gestorUsuarios) {
        Feed feed = new Feed();
        ArrayList<String> lineas = cargar();

        // Sincronizar el contador de Publicacion para no repetir codigos
        int maxContador = 0;

        for (String linea : lineas) {
            if (linea.trim().isEmpty()) continue;
            Publicacion pub = Publicacion.desdeTexto(linea);
            if (pub == null) continue;

            // Resolver autor
            String[] partes = linea.split(";", -1);
            if (partes.length > 1 && !partes[1].isEmpty()) {
                Perfil autor = gestorUsuarios.buscarPorCorreo(partes[1]);
                pub.setAutor(autor);
            }

            // Sincronizar contador
            String sufijo = pub.getCodigo().replace("PUB-", "");
            int idx = desdeBase26(sufijo);
            if (idx > maxContador) maxContador = idx;

            // Agregar al final (el archivo ya esta en orden)
            feed.getPublicaciones().add(pub);
        }

        Publicacion.setContadorGlobal(maxContador);

        // Cargar comentarios
        ArrayList<String> lineasCom = persComentarios.cargar();
        for (String linea : lineasCom) {
            if (linea.trim().isEmpty()) continue;
            String[] p = linea.split(";", -1);
            if (p.length < 4) continue;
            // formato: codigoPub;correoAutor;texto;fecha
            String codigoPub = p[0];
            Publicacion pub  = feed.buscarPorCodigo(codigoPub);
            if (pub == null) continue;

            Perfil autorCom  = gestorUsuarios.buscarPorCorreo(p[1]);
            String texto     = p[2].replace("[SC]", ";").replace("[NL]", "\n");
            String fecha     = p[3];
            pub.agregarComentario(new Comentario(autorCom, texto, fecha));
        }

        return feed;
    }

    private int desdeBase26(String s) {
        int r = 0;
        for (char c : s.toCharArray()) r = r * 26 + (c - 'A' + 1);
        return r;
    }
}
