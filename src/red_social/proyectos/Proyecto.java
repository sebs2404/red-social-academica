/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Proyectos y Equipos
 * CLASE: Proyecto
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.proyectos;

import red_social.usuarios.Perfil;
import java.util.ArrayList;

// Clase principal del modulo 2 que representa un proyecto academico
public class Proyecto {

    private String id;
    private String nombre;
    private String area;
    private String descripcion;
    private EstadoProyecto estado;
    private Perfil lider;
    private ArrayList<Miembro> equipo;
    private ArrayList<Avance> avances;
    private ArrayList<Invitacion> invitaciones;

    // Constructor vacio
    public Proyecto() {
        this.id = "";
        this.nombre = "";
        this.area = "";
        this.descripcion = "";
        this.estado = EstadoProyecto.BORRADOR;
        this.lider = null;
        this.equipo = new ArrayList<>();
        this.avances = new ArrayList<>();
        this.invitaciones = new ArrayList<>();
    }

    // Constructor completo
    public Proyecto(String id, String nombre, String area, String descripcion, Perfil lider) {
        this.id = id;
        this.nombre = nombre;
        this.area = area;
        this.descripcion = descripcion;
        this.estado = EstadoProyecto.BORRADOR;
        this.lider = lider;
        this.equipo = new ArrayList<>();
        this.avances = new ArrayList<>();
        this.invitaciones = new ArrayList<>();
    }

    // Getters
    public String getId() { return this.id; }
    public String getNombre() { return this.nombre; }
    public String getArea() { return this.area; }
    public String getDescripcion() { return this.descripcion; }
    public EstadoProyecto getEstado() { return this.estado; }
    public Perfil getLider() { return this.lider; }
    public ArrayList<Miembro> getEquipo() { return this.equipo; }
    public ArrayList<Avance> getAvances() { return this.avances; }
    public ArrayList<Invitacion> getInvitaciones() { return this.invitaciones; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setArea(String area) { this.area = area; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setLider(Perfil lider) { this.lider = lider; }

    // Cambia el estado del proyecto con validacion
    public void actualizarEstado(EstadoProyecto nuevoEstado) {
        // Regla: un proyecto CERRADO no puede volver a ACTIVO o BORRADOR
        if (this.estado == EstadoProyecto.CERRADO) {
            System.out.println("Error: un proyecto cerrado no puede cambiar de estado.");
            return;
        }
        this.estado = nuevoEstado;
        System.out.println("Estado actualizado a: " + this.estado);
    }

    // Agrega un miembro al equipo
    public void agregarMiembro(Miembro miembro) {
        this.equipo.add(miembro);
        System.out.println("Miembro agregado: " + miembro.getPerfil().getNombre());
    }

    // Publica un nuevo avance con version automatica
    public void publicarAvance(String descripcion, String fecha, String adjunto) {
        int version = this.avances.size() + 1;
        Avance avance = new Avance(version, descripcion, fecha, adjunto);
        this.avances.add(avance);
        System.out.println("Avance v" + version + " publicado correctamente.");
    }

    // Envia una invitacion a un perfil
    public void enviarInvitacion(Perfil destinatario, Rol rol, String fecha) {
        Invitacion inv = new Invitacion(destinatario, this.nombre, rol, fecha);
        this.invitaciones.add(inv);
        System.out.println("Invitacion enviada a: " + destinatario.getNombre());
    }

    // Cierra el proyecto
    public void cerrarProyecto() {
        if (this.equipo.size() == 0) {
            System.out.println("Error: no se puede cerrar un proyecto sin miembros.");
            return;
        }
        this.estado = EstadoProyecto.CERRADO;
        System.out.println("Proyecto cerrado correctamente.");
    }

    // Muestra todos los datos del proyecto
    public void mostrarProyecto() {
        System.out.println("===== PROYECTO =====");
        System.out.println("ID          : " + this.id);
        System.out.println("Nombre      : " + this.nombre);
        System.out.println("Area        : " + this.area);
        System.out.println("Descripcion : " + this.descripcion);
        System.out.println("Estado      : " + this.estado);
        System.out.println("Lider       : " + this.lider.getNombre() + " " + this.lider.getApellido());
        System.out.println("--- Equipo (" + this.equipo.size() + " miembros) ---");
        for (int i = 0; i < this.equipo.size(); i++) {
            this.equipo.get(i).mostrarMiembro();
        }
        System.out.println("--- Avances (" + this.avances.size() + ") ---");
        for (int i = 0; i < this.avances.size(); i++) {
            this.avances.get(i).mostrarAvance();
        }
        System.out.println("====================");
    }

    // Convierte el proyecto a texto para persistencia
    public String aTexto() {
        return this.id + ";" +
               this.nombre + ";" +
               this.area + ";" +
               this.descripcion + ";" +
               this.estado + ";" +
               this.lider.getCorreo();
    }
}
