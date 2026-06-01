/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Usuarios y Perfiles
 * CLASE: Estudiante
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.usuarios;

// Clase hija que representa un estudiante
public class Estudiante extends Perfil {

    private String codigoEstudiantil;
    private int semestre;
    private String programa;

    // Constructor vacio
    public Estudiante() {
        super();
        this.codigoEstudiantil = "";
        this.semestre = 0;
        this.programa = "";
    }

    // Constructor completo
    public Estudiante(String nombre, String apellido, String correo, String contrasena,
                      String institucion, String codigoEstudiantil, int semestre, String programa) {
        super(nombre, apellido, correo, contrasena, institucion);
        this.codigoEstudiantil = codigoEstudiantil;
        this.semestre = semestre;
        this.programa = programa;
    }

    // Getters
    public String getCodigoEstudiantil() { return this.codigoEstudiantil; }
    public int getSemestre() { return this.semestre; }
    public String getPrograma() { return this.programa; }

    // Setters
    public void setCodigoEstudiantil(String codigo) { this.codigoEstudiantil = codigo; }
    public void setSemestre(int semestre) { this.semestre = semestre; }
    public void setPrograma(String programa) { this.programa = programa; }

    @Override
    public void mostrarPerfil() {
        System.out.println("===== PERFIL ESTUDIANTE =====");
        System.out.println("Nombre   : " + this.getNombre() + " " + this.getApellido());
        System.out.println("Correo   : " + this.getCorreo());
        System.out.println("Programa : " + this.programa);
        System.out.println("Semestre : " + this.semestre);
        System.out.println("Codigo   : " + this.codigoEstudiantil);
        System.out.println("Habilidades: " + this.getHabilidades());
        System.out.println("=============================");
    }

    // Formato para guardar en archivo .txt
    // tipo;nombre;apellido;correo;contrasena;institucion;codigo;semestre;programa
    @Override
    public String aTexto() {
        return "ESTUDIANTE;" +
               this.getNombre() + ";" +
               this.getApellido() + ";" +
               this.getCorreo() + ";" +
               this.getContrasena() + ";" +
               this.getInstitucion() + ";" +
               this.codigoEstudiantil + ";" +
               this.semestre + ";" +
               this.programa;
    }
}
