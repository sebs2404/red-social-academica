/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Usuarios y Perfiles
 * CLASE: Administrador
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.usuarios;

// Clase hija que representa un administrador del sistema
public class Administrador extends Perfil {

    private int nivelAcceso;

    // Constructor vacio
    public Administrador() {
        super();
        this.nivelAcceso = 0;
    }

    // Constructor completo
    public Administrador(String nombre, String apellido, String correo, String contrasena,
                         String institucion, int nivelAcceso) {
        super(nombre, apellido, correo, contrasena, institucion);
        this.nivelAcceso = nivelAcceso;
    }

    // Getter
    public int getNivelAcceso() { return this.nivelAcceso; }

    // Setter
    public void setNivelAcceso(int nivelAcceso) { this.nivelAcceso = nivelAcceso; }

    @Override
    public void mostrarPerfil() {
        System.out.println("===== PERFIL ADMINISTRADOR =====");
        System.out.println("Nombre       : " + this.getNombre() + " " + this.getApellido());
        System.out.println("Correo       : " + this.getCorreo());
        System.out.println("Nivel acceso : " + this.nivelAcceso);
        System.out.println("================================");
    }

    // Formato para guardar en archivo .txt
    @Override
    public String aTexto() {
        return "ADMINISTRADOR;" +
               this.getNombre() + ";" +
               this.getApellido() + ";" +
               this.getCorreo() + ";" +
               this.getContrasena() + ";" +
               this.getInstitucion() + ";" +
               this.nivelAcceso;
    }
    //juan hizo esto :)
    @Override
    public boolean puedeCrearTutoria() {
        return true;
    }

    @Override
    public boolean puedeEliminarPublicaciones() {
        return true;
    }

    @Override
    public boolean puedeEliminarEventos() {
        return true;
    }
}
