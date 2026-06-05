/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: Feed
 * AUTOR: [juan silva]
 * FECHA: 2025
 */

package red_social.social;

import java.util.ArrayList;

// Feed social: lista de publicaciones ordenadas del mas nuevo al mas antiguo
public class Feed {

    private ArrayList<Publicacion> publicaciones;

    public Feed() {
        this.publicaciones = new ArrayList<>();
    }

    public ArrayList<Publicacion> getPublicaciones() { return this.publicaciones; }

    // Agrega al inicio para que lo mas reciente aparezca primero
    public void agregarPublicacion(Publicacion publicacion) {
        this.publicaciones.add(0, publicacion);
    }

    // Busca una publicacion por su codigo (PUB-A, PUB-AB, etc.)
    public Publicacion buscarPorCodigo(String codigo) {
        for (Publicacion p : this.publicaciones) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) return p;
        }
        return null;
    }

    // Elimina una publicacion por codigo (para moderacion)
    public boolean eliminarPorCodigo(String codigo) {
        for (int i = 0; i < this.publicaciones.size(); i++) {
            if (this.publicaciones.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                this.publicaciones.remove(i);
                return true;
            }
        }
        return false;
    }

    // Filtra publicaciones por area
    public ArrayList<Publicacion> filtrarPorArea(String area) {
        ArrayList<Publicacion> resultado = new ArrayList<>();
        for (Publicacion p : this.publicaciones) {
            if (p.getArea().equalsIgnoreCase(area)) resultado.add(p);
        }
        return resultado;
    }

    // Filtra publicaciones de un autor especifico
    public ArrayList<Publicacion> filtrarPorAutor(String correo) {
        ArrayList<Publicacion> resultado = new ArrayList<>();
        for (Publicacion p : this.publicaciones) {
            if (p.getAutor() != null && p.getAutor().getCorreo().equalsIgnoreCase(correo))
                resultado.add(p);
        }
        return resultado;
    }

    public int getTotalPublicaciones() { return this.publicaciones.size(); }
}
