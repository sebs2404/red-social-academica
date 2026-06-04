/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: se gestionan los eventos
 * AUTOR: juan esteban silva
 * FECHA:
 */
package red_social.eventos;

import java.util.ArrayList;

import red_social.usuarios.Perfil;
//se crea la clase
public class GestorEventos {

    private ArrayList<Evento> eventos;

    public GestorEventos() {

        eventos = new ArrayList<>();
    }
//accion de agregar un nuevo evento
    public void agregarEvento(
            Evento evento) {

        eventos.add(evento);
    }

    public void eliminarEvento(
            Perfil usuario,
            Evento evento) {
//exepcion para usuarios que no pueden eliminar eventos 
        if(!usuario.puedeEliminarEventos()) {

            throw new IllegalArgumentException(
                    "No tiene permisos");
        }

        eventos.remove(evento);
    }

    public ArrayList<Evento> getEventos() {

        return eventos;
    }
}