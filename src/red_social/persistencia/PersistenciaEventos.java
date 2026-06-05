/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Persistencia
 * CLASE: PersistenciaEventos
 * AUTOR: [juan silva]
 * FECHA: 2025
 */

package red_social.persistencia;

import red_social.eventos.Evento;
import java.util.ArrayList;

// Hereda de Persistencia — guarda y carga eventos del calendario
public class PersistenciaEventos extends Persistencia {

    public PersistenciaEventos() {
        super("datos/eventos.txt");
    }

    public void guardarEventos(ArrayList<Evento> eventos) {
        ArrayList<String> lineas = new ArrayList<>();
        for (Evento e : eventos) lineas.add(e.aTexto());
        guardar(lineas);
    }

    public ArrayList<Evento> cargarEventos() {
        ArrayList<String> lineas = cargar();
        ArrayList<Evento> eventos = new ArrayList<>();
        int maxContador = 0;

        for (String linea : lineas) {
            if (linea.trim().isEmpty()) continue;
            Evento e = Evento.desdeTexto(linea);
            if (e == null) continue;
            eventos.add(e);

            String sufijo = e.getCodigo().replace("EVT-", "");
            int idx = desdeBase26(sufijo);
            if (idx > maxContador) maxContador = idx;
        }

        Evento.setContadorGlobal(maxContador);
        return eventos;
    }

    private int desdeBase26(String s) {
        int r = 0;
        for (char c : s.toCharArray()) r = r * 26 + (c - 'A' + 1);
        return r;
    }
}
