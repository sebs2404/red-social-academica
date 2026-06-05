

package red_social.proyectos;

import red_social.usuarios.Perfil;
import java.util.ArrayList;

// Clase que maneja la lista de proyectos del sistema
public class GestorProyectos {

    private ArrayList<Proyecto> proyectos;

    // Constructor
    public GestorProyectos() {
        this.proyectos = new ArrayList<>();
    }

    // Getter y Setter
    public ArrayList<Proyecto> getProyectos() { return this.proyectos; }
    public void setProyectos(ArrayList<Proyecto> proyectos) { this.proyectos = proyectos; }

    // Registra un nuevo proyecto
    public void registrarProyecto(Proyecto p) {
        proyectos.add(p);
        System.out.println("Proyecto registrado: " + p.getNombre());
    }

    // Busca un proyecto por su ID
    public Proyecto buscarPorId(String id) {
        for (Proyecto p : proyectos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // Busca proyectos por area
    public ArrayList<Proyecto> buscarPorArea(String area) {
        ArrayList<Proyecto> resultado = new ArrayList<>();
        for (Proyecto p : proyectos) {
            if (p.getArea().equalsIgnoreCase(area)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // Busca proyectos por estado
    public ArrayList<Proyecto> buscarPorEstado(EstadoProyecto estado) {
        ArrayList<Proyecto> resultado = new ArrayList<>();
        for (Proyecto p : proyectos) {
            if (p.getEstado() == estado) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // Busca proyectos donde un perfil es miembro
    public ArrayList<Proyecto> buscarPorMiembro(Perfil perfil) {
        ArrayList<Proyecto> resultado = new ArrayList<>();
        for (Proyecto p : proyectos) {
            for (Miembro m : p.getEquipo()) {
                if (m.getPerfil().getCorreo().equals(perfil.getCorreo())) {
                    resultado.add(p);
                    break;
                }
            }
        }
        return resultado;
    }

    // Elimina un proyecto por ID
    public void eliminarProyecto(String id) {
        Proyecto p = buscarPorId(id);
        if (p != null) {
            if (p.getEstado() == EstadoProyecto.ACTIVO) {
                System.out.println("Error: no se puede eliminar un proyecto ACTIVO.");
                return;
            }
            proyectos.remove(p);
            System.out.println("Proyecto eliminado: " + p.getNombre());
        } else {
            System.out.println("Proyecto no encontrado.");
        }
    }

    // Lista todos los proyectos
    public void listarProyectos() {
        if (proyectos.isEmpty()) {
            System.out.println("No hay proyectos registrados.");
            return;
        }
        for (Proyecto p : proyectos) {
            p.mostrarProyecto();
            System.out.println("-------------------");
        }
    }

    // Muestra metricas de los proyectos
    public void mostrarMetricas() {
        int borradores = 0;
        int activos = 0;
        int cerrados = 0;
        int totalMiembros = 0;

        for (Proyecto p : proyectos) {
            if (p.getEstado() == EstadoProyecto.BORRADOR) borradores++;
            if (p.getEstado() == EstadoProyecto.ACTIVO) activos++;
            if (p.getEstado() == EstadoProyecto.CERRADO) cerrados++;
            totalMiembros = totalMiembros + p.getEquipo().size();
        }

        System.out.println("===== METRICAS DE PROYECTOS =====");
        System.out.println("Total proyectos  : " + proyectos.size());
        System.out.println("Borradores       : " + borradores);
        System.out.println("Activos          : " + activos);
        System.out.println("Cerrados         : " + cerrados);
        System.out.println("Total miembros   : " + totalMiembros);
        System.out.println("=================================");
    }
}