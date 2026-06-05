

package red_social.persistencia;

import red_social.proyectos.*;
import red_social.usuarios.Perfil;
import java.util.ArrayList;

// Clase hija que hereda de Persistencia para manejar proyectos
// Sigue el mismo patron que PersistenciaUsuarios de Nicolas
public class PersistenciaProyectos extends Persistencia {

    // Constructor - define el archivo donde se guardan los proyectos
    public PersistenciaProyectos() {
        super("datos/proyectos.txt");
    }

    // Convierte la lista de proyectos a texto y la guarda
    public void guardarProyectos(ArrayList<Proyecto> proyectos) {
        ArrayList<String> lineas = new ArrayList<>();
        for (Proyecto p : proyectos) {
            lineas.add(p.aTexto());
        }
        guardar(lineas);
    }

    // Carga el archivo y reconstruye los objetos proyecto
    // Recibe la lista de perfiles para poder reconstruir el lider
    public ArrayList<Proyecto> cargarProyectos(ArrayList<Perfil> perfiles) {
        ArrayList<String> lineas = cargar();
        ArrayList<Proyecto> proyectos = new ArrayList<>();

        for (String linea : lineas) {
            Proyecto p = convertirLinea(linea, perfiles);
            if (p != null) {
                proyectos.add(p);
            }
        }
        return proyectos;
    }

    // Convierte una linea de texto en un objeto Proyecto
    // Formato: id;nombre;area;descripcion;estado;correoLider
    private Proyecto convertirLinea(String linea, ArrayList<Perfil> perfiles) {
        try {
            String[] d = linea.split(";");

            String id          = d[0];
            String nombre      = d[1];
            String area        = d[2];
            String descripcion = d[3];
            EstadoProyecto estado = EstadoProyecto.valueOf(d[4]);
            String correoLider = d[5];

            // Busca el lider en la lista de perfiles por correo
            Perfil lider = null;
            for (Perfil p : perfiles) {
                if (p.getCorreo().equals(correoLider)) {
                    lider = p;
                    break;
                }
            }

            if (lider == null) {
                System.out.println("Advertencia: lider no encontrado para proyecto " + nombre);
                return null;
            }

            Proyecto proyecto = new Proyecto(id, nombre, area, descripcion, lider);
            proyecto.actualizarEstado(estado);
            return proyecto;

        } catch (Exception e) {
            System.out.println("Error al cargar proyecto: " + e.getMessage());
            return null;
        }
    }
}