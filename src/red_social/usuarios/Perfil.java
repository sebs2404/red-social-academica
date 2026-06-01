/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Usuarios y Perfiles
 * CLASE: Perfil (abstracta)
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.usuarios;

import java.util.ArrayList;

// Clase abstracta padre que define la estructura base de cualquier usuario
public abstract class Perfil {

    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private String institucion;
    private ArrayList<String> habilidades;

    // Constructor vacio
    public Perfil() {
        this.nombre = "";
        this.apellido = "";
        this.correo = "";
        this.contrasena = "";
        this.institucion = "";
        this.habilidades = new ArrayList<>();
    }

    // Constructor completo
    public Perfil(String nombre, String apellido, String correo, String contrasena, String institucion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.institucion = institucion;
        this.habilidades = new ArrayList<>();
    }

    // Getters
    public String getNombre() { return this.nombre; }
    public String getApellido() { return this.apellido; }
    public String getCorreo() { return this.correo; }
    public String getContrasena() { return this.contrasena; }
    public String getInstitucion() { return this.institucion; }
    public ArrayList<String> getHabilidades() { return this.habilidades; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setInstitucion(String institucion) { this.institucion = institucion; }

    // Agrega una habilidad al perfil
    public void agregarHabilidad(String habilidad) {
        this.habilidades.add(habilidad);
    }

    // Verifica si el correo y contrasena coinciden
    public boolean autenticar(String correo, String contrasena) {
        return this.correo.equals(correo) && this.contrasena.equals(contrasena);
    }

    // Metodo abstracto que cada subclase implementara
    public abstract void mostrarPerfil();

    // Metodo abstracto para convertir el objeto a linea de texto para persistencia
    public abstract String aTexto();
}
