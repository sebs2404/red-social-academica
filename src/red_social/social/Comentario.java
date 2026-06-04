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

    // Constructor completo //juan corrigio esto para el uso de exepciones y que no hallan vacios en la creacion de publicaiones y eventos
    public Comentario(Perfil autor,
            String texto,
            String fecha) {

if(autor == null) {
  throw new IllegalArgumentException(
          "El comentario debe tener autor");
}

if(texto == null || texto.isBlank()) {
  throw new IllegalArgumentException(
          "El comentario no puede estar vacio");
}

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
