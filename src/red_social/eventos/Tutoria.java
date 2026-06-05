/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: tutoria
 * AUTOR: juan esteban silva
 * FECHA:
 */
//aca los profesores pueden crear tutorias pra los estudiantes
package red_social.eventos;

public class Tutoria extends Evento {
    
    // Si tu clase tiene atributos propios, como el tutor o la materia:
    private String tutor;
    private String materia;

    // Asegúrate de que el constructor llame al súper constructor reordenado correctamente:
    public Tutoria(String nombre, String fecha, String tipo, String creador, String desc, String tutor, String materia) {
        // Llamada corregida al constructor de Evento de 6 parámetros sin usar null ambiguo
        super(nombre, fecha, tipo, creador, desc, ""); 
        this.tutor = tutor;
        this.materia = materia;
    }
}
