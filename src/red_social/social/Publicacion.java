/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: Publicacion
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.social;

import red_social.usuarios.Perfil;
import red_social.proyectos.Avance;
import red_social.proyectos.Proyecto;
import java.util.ArrayList;

// Clase que representa una publicacion en el feed
public class Publicacion implements AccionesSociales {

    private Perfil autor;
    private String contenido;
    private String fecha;
    private String area;
    private ArrayList<Comentario> comentarios;
    private ArrayList<String> reacciones;
    private ArrayList<Proyecto> suscripciones;

    // Constructor vacio
    public Publicacion() {
        this.autor = null;
        this.contenido = "";
        this.fecha = "";
        this.area = "";
        this.comentarios = new ArrayList<>();
        this.reacciones = new ArrayList<>();
        this.suscripciones = new ArrayList<>();
    }

    // Constructor completo
    public Publicacion(Perfil autor, String contenido, String fecha, String area) {
        this.autor = autor;
        this.contenido = contenido;
        this.fecha = fecha;
        this.area = area;
        this.comentarios = new ArrayList<>();
        this.reacciones = new ArrayList<>();
        this.suscripciones = new ArrayList<>();
    }

    // Getters
    public Perfil getAutor() { return this.autor; }
    public String getContenido() { return this.contenido; }
    public String getFecha() { return this.fecha; }
    public String getArea() { return this.area; }
    public ArrayList<Comentario> getComentarios() { return this.comentarios; }
    public ArrayList<String> getReacciones() { return this.reacciones; }

    // Setters
    public void setAutor(Perfil autor) { this.autor = autor; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setArea(String area) { this.area = area; }

    // Implementacion de AccionesSociales
    @Override
    public void publicarAvance(Avance avance) {
        this.contenido = "Nuevo avance v" + avance.getVersion() + ": " + avance.getDescripcion();
        System.out.println("Avance publicado en el feed.");
    }

    @Override
    public void comentar(String texto) {
        // TODO: recibir el autor del comentario como parametro
        System.out.println("Comentario agregado: " + texto);
    }

    @Override
    public void reaccionar(String tipo) {
        this.reacciones.add(tipo);
        System.out.println("Reaccion '" + tipo + "' agregada.");
    }

    @Override
    public void suscribirse(Proyecto proyecto) {
        this.suscripciones.add(proyecto);
        System.out.println("Suscrito al proyecto: " + proyecto.getNombre());
    }

    // Muestra la publicacion
    public void mostrarPublicacion() {
        System.out.println("===========================");
        System.out.println("[" + this.fecha + "] " + this.autor.getNombre() + " " + this.autor.getApellido());
        System.out.println("Area: " + this.area);
        System.out.println(this.contenido);
        System.out.println("Reacciones: " + this.reacciones.size());
        System.out.println("Comentarios: " + this.comentarios.size());
        for (int i = 0; i < this.comentarios.size(); i++) {
            this.comentarios.get(i).mostrarComentario();
        }
        System.out.println("===========================");
    }

    // Convierte la publicacion a texto para persistencia
    public String aTexto() {
        return this.autor.getCorreo() + ";" +
               this.contenido + ";" +
               this.fecha + ";" +
               this.area;
    }
}
