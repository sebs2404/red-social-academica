/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: AccionesSociales (interface)
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.social;

import red_social.proyectos.Avance;
import red_social.proyectos.Proyecto;

// Interfaz que define las acciones sociales disponibles en el sistema
public interface AccionesSociales {
    void publicarAvance(Avance avance);
    void comentar(String texto);
    void reaccionar(String tipo);
    void suscribirse(Proyecto proyecto);
}
