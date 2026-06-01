/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Proyectos y Equipos
 * CLASE: Miembro
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.proyectos;

import red_social.usuarios.Perfil;

// Clase que representa un miembro dentro de un equipo de proyecto
public class Miembro {

    private Perfil perfil;
    private Rol rol;
    private String fechaIngreso;

    // Constructor vacio
    public Miembro() {
        this.perfil = null;
        this.rol = null;
        this.fechaIngreso = "";
    }

    // Constructor completo
    public Miembro(Perfil perfil, Rol rol, String fechaIngreso) {
        this.perfil = perfil;
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
    }

    // Getters
    public Perfil getPerfil() { return this.perfil; }
    public Rol getRol() { return this.rol; }
    public String getFechaIngreso() { return this.fechaIngreso; }

    // Setters
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
    public void setRol(Rol rol) { this.rol = rol; }
    public void setFechaIngreso(String fecha) { this.fechaIngreso = fecha; }

    // Muestra los datos del miembro
    public void mostrarMiembro() {
        System.out.println("  Miembro : " + this.perfil.getNombre() + " " + this.perfil.getApellido());
        System.out.println("  Rol     : " + this.rol);
        System.out.println("  Ingreso : " + this.fechaIngreso);
    }
}
