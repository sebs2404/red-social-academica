/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: se gestionan los eventos
 * AUTOR: juan esteban silva
 * FECHA:
 */
package red_social.eventos;

import java.util.ArrayList;

/**
 * Gestor oficial que administra la lista de eventos académicos y tutorías.
 */
public class GestorEventos {
    
    private ArrayList<Evento> listaEventos;

    public GestorEventos() {
        this.listaEventos = new ArrayList<>();
    }

    public GestorEventos(ArrayList<Evento> eventosIniciales) {
        this.listaEventos = (eventosIniciales != null) ? eventosIniciales : new ArrayList<>();
    }

    /**
     * Registra un nuevo evento. Envía 6 parámetros en el orden exacto que exige tu método desdeTexto.
     */
    public void agendarEvento(String nombre, String fecha, String tipo, String creador, String descripcion) {
        // Estructura exacta deducida: p[0], p[1], p[2], p[3], desc, p[5]
        // Enviamos "" al final para rellenar la posición p[5] (id del proyecto)
        Evento nuevoEvento = new Evento(nombre, fecha, tipo, creador, descripcion, "");
        this.listaEventos.add(nuevoEvento);
    }

    public ArrayList<Evento> getListaEventos() {
        return this.listaEventos;
    }

    /**
     * Filtra los eventos por creador utilizando el método estático de conversión de texto 
     * para asegurar una compatibilidad del 100% con los atributos de tu compañero.
     */
    public ArrayList<Evento> buscarEventosPorCreador(String correoCreador) {
        ArrayList<Evento> filtrados = new ArrayList<>();
        if (correoCreador == null) return filtrados;

        for (Evento ev : listaEventos) {
            // Usamos el convertidor del archivo original para mapear las posiciones sin que Eclipse falle
            String lineaTexto = ev.toString(); 
            if (lineaTexto != null) {
                String[] p = lineaTexto.split(";", -1);
                // Si la posición del creador (p[3]) coincide con el correo consultado, lo guardamos
                if (p.length >= 4 && p[3].equalsIgnoreCase(correoCreador)) {
                    filtrados.add(ev);
                }
            }
        }
        return filtrados;
    }
}
