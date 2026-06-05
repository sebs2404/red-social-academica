/*
 * NOMBRE DEL PROGRAMA: Red Social Academica para Proyectos y Colaboracion
 * Universidad Distrital Francisco Jose de Caldas
 * Ingenieria de Sistemas — Programacion Orientada a Objetos
 * Docente: Fernando Martinez Rodriguez
 *
 * ESTRUCTURA:
 *   red_social.usuarios     → Perfil, Estudiante, Profesor, Administrador, GestorUsuarios
 *   red_social.proyectos    → Proyecto, Miembro, Avance, Invitacion, enums
 *   red_social.social       → Feed, Publicacion, Comentario, AccionesSociales, VentanaSocial
 *   red_social.eventos      → Evento
 *   red_social.persistencia → Persistencia, PersistenciaUsuarios, PersistenciaSocial, PersistenciaEventos
 */

package red_social;

import red_social.eventos.Evento;
import red_social.persistencia.PersistenciaEventos;
import red_social.persistencia.PersistenciaSocial;
import red_social.persistencia.PersistenciaUsuarios;
import red_social.social.Feed;
import red_social.social.VentanaSocial;
import red_social.usuarios.*;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class RedSocialMain {

    public static void main(String[] args) {

        // Crear carpeta datos si no existe
        new File("datos").mkdirs();

        // ── Persistencia ──────────────────────────────────────────
        PersistenciaUsuarios persUsuarios = new PersistenciaUsuarios();
        PersistenciaSocial   persSocial   = new PersistenciaSocial();
        PersistenciaEventos  persEventos  = new PersistenciaEventos();

        // ── GestorUsuarios: cargar desde archivo ──────────────────
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        ArrayList<Perfil> perfilesCargados = persUsuarios.cargarUsuarios();
        for (Perfil p : perfilesCargados) gestorUsuarios.registrarUsuario(p);

        // Admin por defecto si no hay usuarios (primera vez)
        if (gestorUsuarios.getUsuarios().isEmpty()) {
            Administrador admin = new Administrador(
                "Admin", "Sistema", "admin@ud.edu.co", "admin123",
                "Universidad Distrital", 1
            );
            gestorUsuarios.registrarUsuario(admin);
            persUsuarios.guardarUsuarios(gestorUsuarios.getUsuarios());
            System.out.println("Admin por defecto creado: admin@ud.edu.co / admin123");
        }

        // ── Feed y Eventos: cargar desde archivo ──────────────────
        Feed feed             = persSocial.cargarFeed(gestorUsuarios);
        ArrayList<Evento> evs = persEventos.cargarEventos();

        // ── Lanzar GUI login (modulo 1) y luego abrir modulo 3 ────
        SwingUtilities.invokeLater(() -> {
            VentanaLogin login = new VentanaLogin(gestorUsuarios);
            // VentanaLogin ya muestra VentanaAdministrador o VentanaUsuarios al autenticar.
            // Para integrar con el modulo 3, VentanaLogin debe llamar a abrirModuloSocial()
            // cuando el usuario inicie sesion. Ver comentario en VentanaLogin.
            //
            // INTEGRACION RAPIDA PARA PRUEBAS — descomentar si quieres saltar el login:
            // abrirModuloSocial(gestorUsuarios.buscarPorCorreo("admin@ud.edu.co"),
            //                   gestorUsuarios, feed, evs, persSocial, persEventos);
        });
    }

    /*
     * Abre la ventana del modulo 3.
     * El modulo 1 (VentanaLogin) debe llamar este metodo luego de autenticar.
     * Ejemplo en VentanaLogin.ingresar():
     *
     *   RedSocialMain.abrirModuloSocial(p, gestor, feed, eventos, persSocial, persEventos);
     *
     * O bien cada modulo puede tener su propia ventana y esta es solo la del modulo 3.
     */
    public static void abrirModuloSocial(Perfil usuario,
                                         GestorUsuarios gestorUsuarios,
                                         Feed feed,
                                         ArrayList<Evento> eventos,
                                         PersistenciaSocial persSocial,
                                         PersistenciaEventos persEventos) {
        SwingUtilities.invokeLater(() ->
            new VentanaSocial(usuario, gestorUsuarios, feed, eventos, persSocial, persEventos)
        );
    }
}
