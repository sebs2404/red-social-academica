/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Persistencia
 * CLASE: PersistenciaUsuarios
 * AUTOR: Nicolas Vargas Chavarro
 * FECHA: 2025
 */

package red_social.persistencia;

import red_social.usuarios.*;
import java.util.ArrayList;

// Clase hija que hereda de Persistencia para manejar usuarios
public class PersistenciaUsuarios extends Persistencia {

    // Constructor - define el archivo donde se guardan los usuarios
    public PersistenciaUsuarios() {
        super("datos/usuarios.txt");
    }

    // Convierte la lista de usuarios a texto y la guarda
    public void guardarUsuarios(ArrayList<Perfil> usuarios) {
        ArrayList<String> lineas = new ArrayList<>();
        for (Perfil p : usuarios) {
            lineas.add(p.aTexto());
        }
        guardar(lineas);
    }

    // Carga el archivo y reconstruye los objetos usuarios
    public ArrayList<Perfil> cargarUsuarios() {
        ArrayList<String> lineas = cargar();
        ArrayList<Perfil> usuarios = new ArrayList<>();
        for (String linea : lineas) {
            Perfil p = convertirLinea(linea);
            if (p != null) {
                usuarios.add(p);
            }
        }
        return usuarios;
    }

    // Convierte una linea de texto en un objeto Perfil
    // Formato: TIPO;nombre;apellido;correo;contrasena;institucion;...
    private Perfil convertirLinea(String linea) {
        String[] d = linea.split(";");

        if (d[0].equals("ESTUDIANTE")) {
            // ESTUDIANTE;nombre;apellido;correo;contrasena;institucion;codigo;semestre;programa
            Estudiante e = new Estudiante(
                d[1], d[2], d[3], d[4], d[5], d[6],
                Integer.parseInt(d[7]), d[8]
            );
            return e;

        } else if (d[0].equals("PROFESOR")) {
            // PROFESOR;nombre;apellido;correo;contrasena;institucion;titulo;departamento
            Profesor p = new Profesor(
                d[1], d[2], d[3], d[4], d[5], d[6], d[7]
            );
            return p;

        } else if (d[0].equals("ADMINISTRADOR")) {
            // ADMINISTRADOR;nombre;apellido;correo;contrasena;institucion;nivelAcceso
            return new Administrador(
                d[1], d[2], d[3], d[4], d[5],
                Integer.parseInt(d[6])
            );
        }
        return null;
    }
}