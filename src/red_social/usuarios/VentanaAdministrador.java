/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Usuarios y Perfiles
 * CLASE: VentanaAdministrador
 * AUTOR: Nicolas Vargas Chavarro
 * FECHA: 2025
 */

package red_social.usuarios;

import red_social.persistencia.PersistenciaUsuarios;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Ventana de administracion para moderar usuarios y ver estadisticas
public class VentanaAdministrador extends JFrame {

    private GestorUsuarios gestor;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public VentanaAdministrador(GestorUsuarios gestor) {
        this.gestor = gestor;

        setTitle("Panel Administrador");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Titulo
        JLabel lblTitulo = new JLabel("  PANEL DE ADMINISTRACION", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnEliminar   = new JButton("Eliminar Usuario");
        JButton btnEstadisticas = new JButton("Ver Estadisticas");
        JButton btnBuscar     = new JButton("Buscar por Habilidad");

        panelBotones.add(btnEliminar);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnBuscar);

        // Tabla de usuarios
        String[] columnas = {"Tipo", "Nombre", "Apellido", "Correo", "Institucion"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        actualizarTabla();

        // Elimina usuario seleccionado
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario.");
                return;
            }
            String correo = (String) modeloTabla.getValueAt(fila, 3);
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar usuario " + correo + "?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                gestor.eliminarUsuario(correo);
                PersistenciaUsuarios pers = new PersistenciaUsuarios();
                pers.guardarUsuarios(gestor.getUsuarios());
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Usuario eliminado.");
            }
        });

        // Muestra estadisticas globales
        btnEstadisticas.addActionListener(e -> {
            int estudiantes = 0, profesores = 0, admins = 0;
            for (Perfil p : gestor.getUsuarios()) {
                if (p instanceof Estudiante)     estudiantes++;
                else if (p instanceof Profesor)  profesores++;
                else if (p instanceof Administrador) admins++;
            }
            JOptionPane.showMessageDialog(this,
                "=== ESTADISTICAS GLOBALES ===\n" +
                "Estudiantes    : " + estudiantes + "\n" +
                "Profesores     : " + profesores  + "\n" +
                "Administradores: " + admins       + "\n" +
                "Total usuarios : " + gestor.getUsuarios().size()
            );
        });

        // Busca por habilidad
        btnBuscar.addActionListener(e -> {
            String hab = JOptionPane.showInputDialog(this, "Habilidad a buscar:");
            if (hab == null || hab.trim().isEmpty()) return;
            StringBuilder sb = new StringBuilder("Usuarios con '" + hab + "':\n\n");
            boolean encontrado = false;
            for (Perfil p : gestor.getUsuarios()) {
                if (p.getHabilidades().contains(hab.trim())) {
                    sb.append("- ").append(p.getNombre()).append(" ")
                      .append(p.getApellido()).append("\n");
                    encontrado = true;
                }
            }
            if (!encontrado) sb.append("Ninguno encontrado.");
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        setVisible(true);
    }

    // Actualiza la tabla con todos los usuarios
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Perfil p : gestor.getUsuarios()) {
            String tipo = p instanceof Estudiante    ? "Estudiante"     :
                          p instanceof Profesor      ? "Profesor"       : "Administrador";
            modeloTabla.addRow(new Object[]{
                tipo, p.getNombre(), p.getApellido(),
                p.getCorreo(), p.getInstitucion()
            });
        }
    }
}