
/*
 * NOMBRE DEL PROGRAMA: Red Social Academica para Proyectos y Colaboracion
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 *
 * ESTRUCTURA:
 *   red_social.usuarios     → Perfil, Estudiante, Profesor, Administrador
 *   red_social.proyectos    → Proyecto, Miembro, Avance, Invitacion, enums
 *   red_social.social       → Feed, Publicacion, Comentario, AccionesSociales
 *   red_social.eventos      → Evento
 *   red_social.persistencia → Persistencia
 */

package red_social;

import red_social.usuarios.*;
import red_social.proyectos.*;
import red_social.social.*;
import red_social.eventos.*;
import red_social.persistencia.*;
import java.util.ArrayList;

public class RedSocialMain {

    // Listas principales del sistema
    static ArrayList<Perfil> perfiles = new ArrayList<>();
    static ArrayList<Proyecto> proyectos = new ArrayList<>();
    static ArrayList<Evento> eventos = new ArrayList<>();
    static Feed feed = new Feed();

    // Objetos de persistencia
    static Persistencia persUsuarios  = new Persistencia("datos/usuarios.txt");
    static Persistencia persProyectos = new Persistencia("datos/proyectos.txt");
    static Persistencia persEventos   = new Persistencia("datos/eventos.txt");

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("  RED SOCIAL ACADEMICA");
        System.out.println("  Universidad Distrital Francisco Jose de Caldas");
        System.out.println("==========================================\n");

        // TODO: cargar datos desde archivos al iniciar
        // TODO: implementar menu principal
        // TODO: guardar datos en archivos al cerrar

        System.out.println("Sistema iniciado correctamente.");
        System.out.println("Modulos listos para implementar:");
        System.out.println("  [ ] Modulo 1 - Usuarios y Perfiles");
        System.out.println("  [ ] Modulo 2 - Proyectos y Equipos");
        System.out.println("  [ ] Modulo 3 - Social, Eventos y Reportes");
    }
}