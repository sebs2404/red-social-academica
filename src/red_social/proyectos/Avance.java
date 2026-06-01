/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Proyectos y Equipos
 * CLASE: Avance
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.proyectos;

// Clase que representa un avance publicado dentro de un proyecto
public class Avance {

    private int version;
    private String descripcion;
    private String fecha;
    private String adjunto;

    // Constructor vacio
    public Avance() {
        this.version = 0;
        this.descripcion = "";
        this.fecha = "";
        this.adjunto = "";
    }

    // Constructor completo
    public Avance(int version, String descripcion, String fecha, String adjunto) {
        this.version = version;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.adjunto = adjunto;
    }

    // Getters
    public int getVersion() { return this.version; }
    public String getDescripcion() { return this.descripcion; }
    public String getFecha() { return this.fecha; }
    public String getAdjunto() { return this.adjunto; }

    // Setters
    public void setVersion(int version) { this.version = version; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setAdjunto(String adjunto) { this.adjunto = adjunto; }

    // Muestra los datos del avance
    public void mostrarAvance() {
        System.out.println("  Version   : v" + this.version);
        System.out.println("  Fecha     : " + this.fecha);
        System.out.println("  Descripcion: " + this.descripcion);
        System.out.println("  Adjunto   : " + this.adjunto);
    }

    // Convierte el avance a texto para persistencia
    public String aTexto() {
        return this.version + ";" + this.descripcion + ";" + this.fecha + ";" + this.adjunto;
    }
}
