/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: Comentario
 * AUTOR: [jaun silva]
 * FECHA: 2025
 */

package red_social.social;

import red_social.usuarios.Perfil;

// Comentario dentro de una publicacion del feed
public class Comentario {

    private Perfil autor;
    private String texto;
    private String fecha;

    public Comentario() {
        this.autor = null;
        this.texto = "";
        this.fecha = "";
    }

    public Comentario(Perfil autor, String texto, String fecha) {
        this.autor = autor;
        this.texto = texto;
        this.fecha = fecha;
    }

    public Perfil getAutor() { return this.autor; }
    public String getTexto() { return this.texto; }
    public String getFecha() { return this.fecha; }

    public void setAutor(Perfil autor) { this.autor = autor; }
    public void setTexto(String texto) { this.texto = texto; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public void mostrarComentario() {
        String nombre = (this.autor != null)
            ? this.autor.getNombre() + " " + this.autor.getApellido()
            : "Anonimo";
        System.out.println("  [" + this.fecha + "] " + nombre + ": " + this.texto);
    }

    // Formato: correoAutor;texto;fecha
    public String aTexto(String codigoPublicacion) {
        String tSafe = this.texto.replace(";", "[SC]").replace("\n", "[NL]");
        String correo= (this.autor != null) ? this.autor.getCorreo() : "";
        return codigoPublicacion + ";" + correo + ";" + tSafe + ";" + this.fecha;
    }
}
