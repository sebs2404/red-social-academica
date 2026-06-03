/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Usuarios y Perfiles
 * CLASE: GestorUsuarios
 * AUTOR: Nicolas Vargas Chavarro
 * FECHA: 2025
 */

package red_social.usuarios;

import java.util.ArrayList;

// Clase que maneja la lista de usuarios del sistema
public class GestorUsuarios {

    // Lista donde se guardan todos los usuarios
    private ArrayList<Perfil> usuarios;

    // Constructor
    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    // Agrega un usuario a la lista
    public void registrarUsuario(Perfil p) {
        usuarios.add(p);
        System.out.println("Usuario registrado: " + p.getNombre());
    }

    // Busca un usuario por su correo
    public Perfil buscarPorCorreo(String correo) {
        for (Perfil p : usuarios) {
            if (p.getCorreo().equals(correo)) {
                return p;
            }
        }
        return null;
    }

    // Elimina un usuario por correo
    public void eliminarUsuario(String correo) {
        Perfil p = buscarPorCorreo(correo);
        if (p != null) {
            usuarios.remove(p);
            System.out.println("Usuario eliminado.");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    // Muestra todos los usuarios
    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Perfil p : usuarios) {
                p.mostrarPerfil();
                System.out.println("-------------------");
            }
        }
    }

    // Busca usuarios por una habilidad
    public ArrayList<Perfil> buscarPorHabilidad(String habilidad) {
        ArrayList<Perfil> resultado = new ArrayList<>();
        for (Perfil p : usuarios) {
            if (p.getHabilidades().contains(habilidad)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // Muestra cuantos estudiantes y profesores hay
    public void mostrarMetricas() {
        int estudiantes = 0;
        int profesores = 0;
        for (Perfil p : usuarios) {
            if (p instanceof Estudiante) estudiantes++;
            if (p instanceof Profesor) profesores++;
        }
        System.out.println("Estudiantes: " + estudiantes);
        System.out.println("Profesores : " + profesores);
        System.out.println("Total      : " + usuarios.size());
    }

    // Getter de la lista
    public ArrayList<Perfil> getUsuarios() {
        return usuarios;
    }

    // Setter de la lista
    public void setUsuarios(ArrayList<Perfil> usuarios) {
        this.usuarios = usuarios;
    }
}