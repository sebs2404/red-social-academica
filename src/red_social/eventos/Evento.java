/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: Evento
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.eventos;

import red_social.proyectos.Proyecto;

// Clase que representa un evento academico (entrega, presentacion, etc.)
public class Evento {

    private String nombre;
    private String fecha;
    private String tipo; // ENTREGA, PRESENTACION, REUNION
    private String descripcion;
    private Proyecto proyecto;

    // Constructor vacio
    public Evento() {
        this.nombre = "";
        this.fecha = "";
        this.tipo = "";
        this.descripcion = "";
        this.proyecto = null;
    }

    // Constructor completo
    public Evento(String nombre, String fecha, String tipo, String descripcion, Proyecto proyecto) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.proyecto = proyecto;
    }

    // Getters
    public String getNombre() { return this.nombre; }
    public String getFecha() { return this.fecha; }
    public String getTipo() { return this.tipo; }
    public String getDescripcion() { return this.descripcion; }
    public Proyecto getProyecto() { return this.proyecto; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    // Muestra los datos del evento
    public void mostrarEvento() {
        System.out.println("  Evento      : " + this.nombre);
        System.out.println("  Tipo        : " + this.tipo);
        System.out.println("  Fecha       : " + this.fecha);
        System.out.println("  Descripcion : " + this.descripcion);
        if (this.proyecto != null) {
            System.out.println("  Proyecto    : " + this.proyecto.getNombre());
        }
    }

    // Convierte el evento a texto para persistencia
    public String aTexto() {
        String idProyecto = (this.proyecto != null) ? this.proyecto.getId() : "SIN_PROYECTO";
        return this.nombre + ";" + this.fecha + ";" + this.tipo + ";" + this.descripcion + ";" + idProyecto;
    }
}
