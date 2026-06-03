/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Usuarios y Perfiles
 * CLASE: VentanaUsuarios
 * AUTOR: Nicolas Vargas Chavarro
 * FECHA: 2025
 */

package red_social.usuarios;

import red_social.persistencia.PersistenciaUsuarios;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Ventana principal para gestionar usuarios con Java Swing
public class VentanaUsuarios extends JFrame {

    private GestorUsuarios gestor;
    private PersistenciaUsuarios persistencia;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public VentanaUsuarios(GestorUsuarios gestor) {
        this.gestor = gestor;
        this.persistencia = new PersistenciaUsuarios();

        // Carga usuarios guardados al abrir
        gestor.setUsuarios(persistencia.cargarUsuarios());

        // Configuracion de la ventana
        setTitle("Gestion de Usuarios");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnEstudiante = new JButton("Registrar Estudiante");
        JButton btnProfesor   = new JButton("Registrar Profesor");
        JButton btnEliminar   = new JButton("Eliminar Usuario");
        JButton btnBuscar     = new JButton("Buscar por Habilidad");
        JButton btnMetricas   = new JButton("Ver Metricas");

        panelBotones.add(btnEstudiante);
        panelBotones.add(btnProfesor);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnMetricas);

        // Tabla para mostrar usuarios
        String[] columnas = {"Tipo", "Nombre", "Apellido", "Correo", "Institucion"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        add(panelBotones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        actualizarTabla();

        // Eventos
        btnEstudiante.addActionListener(e -> registrarEstudiante());
        btnProfesor.addActionListener(e -> registrarProfesor());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnBuscar.addActionListener(e -> buscarHabilidad());
        btnMetricas.addActionListener(e -> mostrarMetricas());

        setVisible(true);
    }

    // Actualiza la tabla con los usuarios actuales
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Perfil p : gestor.getUsuarios()) {
            String tipo = p instanceof Estudiante ? "Estudiante" :
                          p instanceof Profesor   ? "Profesor"   : "Administrador";
            modeloTabla.addRow(new Object[]{
                tipo, p.getNombre(), p.getApellido(),
                p.getCorreo(), p.getInstitucion()
            });
        }
    }

    // Formulario para registrar estudiante
    private void registrarEstudiante() {
        JTextField txtNombre     = new JTextField();
        JTextField txtApellido   = new JTextField();
        JTextField txtCorreo     = new JTextField();
        JTextField txtContrasena = new JTextField();
        JTextField txtInstitucion= new JTextField();
        JTextField txtCodigo     = new JTextField();
        JTextField txtSemestre   = new JTextField();
        JTextField txtPrograma   = new JTextField();
        JTextField txtHabilidades= new JTextField();

        Object[] campos = {
            "Nombre:",        txtNombre,
            "Apellido:",      txtApellido,
            "Correo:",        txtCorreo,
            "Contrasena:",    txtContrasena,
            "Institucion:",   txtInstitucion,
            "Codigo:",        txtCodigo,
            "Semestre:",      txtSemestre,
            "Programa:",      txtPrograma,
            "Habilidades (separadas por coma):", txtHabilidades
        };

        int r = JOptionPane.showConfirmDialog(this, campos,
                "Registrar Estudiante", JOptionPane.OK_CANCEL_OPTION);

        if (r == JOptionPane.OK_OPTION) {
            try {
                Estudiante e = new Estudiante(
                    txtNombre.getText(), txtApellido.getText(),
                    txtCorreo.getText(), txtContrasena.getText(),
                    txtInstitucion.getText(), txtCodigo.getText(),
                    Integer.parseInt(txtSemestre.getText()),
                    txtPrograma.getText()
                );
                for (String h : txtHabilidades.getText().split(",")) {
                    if (!h.trim().isEmpty()) e.agregarHabilidad(h.trim());
                }
                gestor.registrarUsuario(e);
                persistencia.guardarUsuarios(gestor.getUsuarios());
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Estudiante registrado.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    // Formulario para registrar profesor
    private void registrarProfesor() {
        JTextField txtNombre     = new JTextField();
        JTextField txtApellido   = new JTextField();
        JTextField txtCorreo     = new JTextField();
        JTextField txtContrasena = new JTextField();
        JTextField txtInstitucion= new JTextField();
        JTextField txtTitulo     = new JTextField();
        JTextField txtDepto      = new JTextField();
        JTextField txtHabilidades= new JTextField();

        Object[] campos = {
            "Nombre:",        txtNombre,
            "Apellido:",      txtApellido,
            "Correo:",        txtCorreo,
            "Contrasena:",    txtContrasena,
            "Institucion:",   txtInstitucion,
            "Titulo:",        txtTitulo,
            "Departamento:",  txtDepto,
            "Habilidades (separadas por coma):", txtHabilidades
        };

        int r = JOptionPane.showConfirmDialog(this, campos,
                "Registrar Profesor", JOptionPane.OK_CANCEL_OPTION);

        if (r == JOptionPane.OK_OPTION) {
            try {
                Profesor p = new Profesor(
                    txtNombre.getText(), txtApellido.getText(),
                    txtCorreo.getText(), txtContrasena.getText(),
                    txtInstitucion.getText(), txtTitulo.getText(),
                    txtDepto.getText()
                );
                for (String h : txtHabilidades.getText().split(",")) {
                    if (!h.trim().isEmpty()) p.agregarHabilidad(h.trim());
                }
                gestor.registrarUsuario(p);
                persistencia.guardarUsuarios(gestor.getUsuarios());
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Profesor registrado.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    // Elimina el usuario seleccionado en la tabla
    private void eliminarUsuario() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario de la tabla.");
            return;
        }
        String correo = (String) modeloTabla.getValueAt(fila, 3);
        gestor.eliminarUsuario(correo);
        persistencia.guardarUsuarios(gestor.getUsuarios());
        actualizarTabla();
        JOptionPane.showMessageDialog(this, "Usuario eliminado.");
    }

    // Busca usuarios por habilidad
    private void buscarHabilidad() {
        String hab = JOptionPane.showInputDialog(this, "Habilidad a buscar:");
        if (hab == null || hab.trim().isEmpty()) return;

        ArrayList<Perfil> resultado = gestor.buscarPorHabilidad(hab.trim());

        if (resultado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nadie tiene esa habilidad.");
        } else {
            StringBuilder sb = new StringBuilder("Usuarios con '" + hab + "':\n\n");
            for (Perfil p : resultado) {
                sb.append("- ").append(p.getNombre()).append(" ")
                  .append(p.getApellido()).append(" (")
                  .append(p.getCorreo()).append(")\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }

    // Muestra metricas del sistema
    private void mostrarMetricas() {
        int estudiantes = 0, profesores = 0;
        for (Perfil p : gestor.getUsuarios()) {
            if (p instanceof Estudiante) estudiantes++;
            if (p instanceof Profesor)   profesores++;
        }
        JOptionPane.showMessageDialog(this,
            "Estudiantes : " + estudiantes + "\n" +
            "Profesores  : " + profesores  + "\n" +
            "Total       : " + gestor.getUsuarios().size()
        );
    }
}