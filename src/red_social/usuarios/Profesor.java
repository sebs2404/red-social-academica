/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Usuarios y Perfiles
 * CLASE: Profesor
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.usuarios;

// Clase hija que representa un profesor
public class Profesor extends Perfil {

    private String titulo;
    private String departamento;

    // Constructor vacio
    public Profesor() {
        super();
        this.titulo = "";
        this.departamento = "";
    }

    // Constructor completo
    public Profesor(String nombre, String apellido, String correo, String contrasena,
                    String institucion, String titulo, String departamento) {
        super(nombre, apellido, correo, contrasena, institucion);
        this.titulo = titulo;
        this.departamento = departamento;
    }

    // Getters
    public String getTitulo() { return this.titulo; }
    public String getDepartamento() { return this.departamento; }

    // Setters
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    @Override
    public void mostrarPerfil() {
        System.out.println("===== PERFIL PROFESOR =====");
        System.out.println("Nombre       : " + this.getNombre() + " " + this.getApellido());
        System.out.println("Correo       : " + this.getCorreo());
        System.out.println("Titulo       : " + this.titulo);
        System.out.println("Departamento : " + this.departamento);
        System.out.println("Habilidades  : " + this.getHabilidades());
        System.out.println("===========================");
    }

    // Formato para guardar en archivo .txt
    // tipo;nombre;apellido;correo;contrasena;institucion;titulo;departamento
    @Override
    public String aTexto() {
        return "PROFESOR;" +
               this.getNombre() + ";" +
               this.getApellido() + ";" +
               this.getCorreo() + ";" +
               this.getContrasena() + ";" +
               this.getInstitucion() + ";" +
               this.titulo + ";" +
               this.departamento;
    }
}
