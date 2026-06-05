package red_social;

import red_social.usuarios.*;
import red_social.proyectos.*;
import red_social.social.*;
import red_social.eventos.*;
import red_social.persistencia.*;
import javax.swing.*;
import java.util.ArrayList;

public class RedSocialMain {

    // Gestores compartidos
    static GestorUsuarios  gestorUsuarios  = new GestorUsuarios();
    static GestorProyectos gestorProyectos = new GestorProyectos();
    static Feed            feed            = new Feed();
    static ArrayList<Evento> eventos       = new ArrayList<>();

    // Persistencia
    static PersistenciaUsuarios  persUsuarios  = new PersistenciaUsuarios();
    static PersistenciaProyectos persProyectos = new PersistenciaProyectos();
    static PersistenciaSocial    persSocial    = new PersistenciaSocial();
    static PersistenciaEventos   persEventos   = new PersistenciaEventos();

    public static void main(String[] args) {

        // Cargar datos guardados al iniciar
        gestorUsuarios.setUsuarios(persUsuarios.cargarUsuarios());
        gestorProyectos.setProyectos(persProyectos.cargarProyectos(
                gestorUsuarios.getUsuarios()));
        Feed feedCargado = persSocial.cargarFeed(gestorUsuarios);
        feed.getPublicaciones().addAll(feedCargado.getPublicaciones());
        eventos.addAll(persEventos.cargarEventos());

        // Abrir login
        SwingUtilities.invokeLater(() -> {
            new VentanaLogin(gestorUsuarios);
        });
    }

    // Metodo para abrir la ventana social despues del login
    public static void abrirVentanaSocial(Perfil usuarioSesion) {
        new VentanaSocial(
            usuarioSesion,
            gestorUsuarios,
            feed,
            eventos,
            persSocial,
            persEventos
        );
    }
}