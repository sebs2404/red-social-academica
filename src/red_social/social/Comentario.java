/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: Comentario
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.social;

import red_social.usuarios.Perfil;

// Clase que representa un comentario en una publicacion
public class Comentario {

    private Perfil autor;
    private String texto;
    private String fecha;

    // Constructor vacio
    public Comentario() {
        this.autor = null;
        this.texto = "";
        this.fecha = "";
    }

    // Constructor completo
    public Comentario(Perfil autor, String texto, String fecha) {
        this.autor = autor;
        this.texto = texto;
        this.fecha = fecha;
    }

    // Getters
    public Perfil getAutor() { return this.autor; }
    public String getTexto() { return this.texto; }
    public String getFecha() { return this.fecha; }

    // Setters
    public void setAutor(Perfil autor) { this.autor = autor; }
    public void setTexto(String texto) { this.texto = texto; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    // Muestra el comentario
    public void mostrarComentario() {
        System.out.println("  [" + this.fecha + "] " +
                           this.autor.getNombre() + ": " + this.texto);
    }
}
