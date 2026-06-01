/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Proyectos y Equipos
 * CLASE: Invitacion
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.proyectos;

import red_social.usuarios.Perfil;

// Clase que representa una invitacion a participar en un proyecto
public class Invitacion {

    private Perfil destinatario;
    private String nombreProyecto;
    private Rol rolPropuesto;
    private String estado; // PENDIENTE, ACEPTADA, RECHAZADA
    private String fecha;

    // Constructor vacio
    public Invitacion() {
        this.destinatario = null;
        this.nombreProyecto = "";
        this.rolPropuesto = null;
        this.estado = "PENDIENTE";
        this.fecha = "";
    }

    // Constructor completo
    public Invitacion(Perfil destinatario, String nombreProyecto, Rol rolPropuesto, String fecha) {
        this.destinatario = destinatario;
        this.nombreProyecto = nombreProyecto;
        this.rolPropuesto = rolPropuesto;
        this.estado = "PENDIENTE";
        this.fecha = fecha;
    }

    // Getters
    public Perfil getDestinatario() { return this.destinatario; }
    public String getNombreProyecto() { return this.nombreProyecto; }
    public Rol getRolPropuesto() { return this.rolPropuesto; }
    public String getEstado() { return this.estado; }
    public String getFecha() { return this.fecha; }

    // Setters
    public void setDestinatario(Perfil destinatario) { this.destinatario = destinatario; }
    public void setNombreProyecto(String nombre) { this.nombreProyecto = nombre; }
    public void setRolPropuesto(Rol rol) { this.rolPropuesto = rol; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    // Acciones sobre la invitacion
    public void aceptar() { this.estado = "ACEPTADA"; }
    public void rechazar() { this.estado = "RECHAZADA"; }

    // Muestra los datos de la invitacion
    public void mostrarInvitacion() {
        System.out.println("  Destinatario : " + this.destinatario.getNombre() + " " + this.destinatario.getApellido());
        System.out.println("  Proyecto     : " + this.nombreProyecto);
        System.out.println("  Rol propuesto: " + this.rolPropuesto);
        System.out.println("  Estado       : " + this.estado);
        System.out.println("  Fecha        : " + this.fecha);
    }
}
