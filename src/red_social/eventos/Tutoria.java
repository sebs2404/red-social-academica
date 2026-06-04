/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: tutoria
 * AUTOR: juan esteban silva
 * FECHA:
 */
//aca los profesores pueden crear tutorias pra los estudiantes
package red_social.eventos;
//se importa de otras clases
import red_social.proyectos.Proyecto;
import red_social.usuarios.Profesor;

public class Tutoria extends Evento {

    private Profesor tutor;

    public Tutoria(String nombre,
                   String fecha,
                   String descripcion,
                   Proyecto proyecto,
                   Profesor tutor) {

        super(nombre,
              fecha,
              "TUTORIA",
              descripcion,
              proyecto);
//exepcion para usuarios sina cceso a crear tutorias
        if(tutor == null) {
            throw new IllegalArgumentException(
                    "La tutoria requiere un profesor");
        }

        this.tutor = tutor;
    }

    public Profesor getTutor() {
        return tutor;
    }

    @Override
    public void mostrarEvento() {

        super.mostrarEvento();

        System.out.println(
                "Tutor: "
                + tutor.getNombre()
                + " "
                + tutor.getApellido());
    }
}