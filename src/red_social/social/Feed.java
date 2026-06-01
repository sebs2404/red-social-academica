/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: Feed
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.social;

import java.util.ArrayList;

// Clase que representa el feed social del sistema
public class Feed {

    private ArrayList<Publicacion> publicaciones;

    // Constructor
    public Feed() {
        this.publicaciones = new ArrayList<>();
    }

    // Getter
    public ArrayList<Publicacion> getPublicaciones() { return this.publicaciones; }

    // Agrega una publicacion al feed
    public void agregarPublicacion(Publicacion publicacion) {
        this.publicaciones.add(publicacion);
        System.out.println("Publicacion agregada al feed.");
    }

    // Muestra todas las publicaciones
    public void mostrarFeed() {
        if (this.publicaciones.size() == 0) {
            System.out.println("El feed esta vacio.");
            return;
        }
        System.out.println("===== FEED =====");
        for (int i = 0; i < this.publicaciones.size(); i++) {
            this.publicaciones.get(i).mostrarPublicacion();
        }
    }

    // Filtra publicaciones por area
    public void filtrarPorArea(String area) {
        System.out.println("===== FEED - AREA: " + area + " =====");
        boolean encontro = false;
        for (int i = 0; i < this.publicaciones.size(); i++) {
            if (this.publicaciones.get(i).getArea().equalsIgnoreCase(area)) {
                this.publicaciones.get(i).mostrarPublicacion();
                encontro = true;
            }
        }
        if (!encontro) {
            System.out.println("No hay publicaciones en esta area.");
        }
    }
}
