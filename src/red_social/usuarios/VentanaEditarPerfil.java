/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Usuarios y Perfiles
 * CLASE: VentanaEditarPerfil
 * AUTOR: Nicolas Vargas Chavarro
 * FECHA: 2025
 */

package red_social.usuarios;

import red_social.persistencia.PersistenciaUsuarios;
import javax.swing.*;
import java.awt.*;

// Ventana para editar los datos de un perfil existente
public class VentanaEditarPerfil extends JFrame {

    private GestorUsuarios gestor;
    private Perfil perfil;

    public VentanaEditarPerfil(GestorUsuarios gestor, Perfil perfil) {
        this.gestor  = gestor;
        this.perfil  = perfil;

        setTitle("Editar Perfil - " + perfil.getNombre());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Campos con datos actuales
        JTextField txtNombre      = new JTextField(perfil.getNombre());
        JTextField txtApellido    = new JTextField(perfil.getApellido());
        JTextField txtInstitucion = new JTextField(perfil.getInstitucion());
        JTextField txtContrasena  = new JTextField(perfil.getContrasena());
        JTextField txtHabilidad   = new JTextField();

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Apellido:"));
        panelForm.add(txtApellido);
        panelForm.add(new JLabel("Institucion:"));
        panelForm.add(txtInstitucion);
        panelForm.add(new JLabel("Contrasena:"));
        panelForm.add(txtContrasena);
        panelForm.add(new JLabel("Agregar habilidad:"));
        panelForm.add(txtHabilidad);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar cambios");
        JButton btnHabilidad = new JButton("Agregar habilidad");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnHabilidad);

        add(new JLabel("  Editar Perfil", SwingConstants.LEFT), BorderLayout.NORTH);
        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Guarda los cambios
        btnGuardar.addActionListener(e -> {
            perfil.setNombre(txtNombre.getText());
            perfil.setApellido(txtApellido.getText());
            perfil.setInstitucion(txtInstitucion.getText());
            perfil.setContrasena(txtContrasena.getText());

            PersistenciaUsuarios pers = new PersistenciaUsuarios();
            pers.guardarUsuarios(gestor.getUsuarios());

            JOptionPane.showMessageDialog(this, "Perfil actualizado.");
            dispose();
        });

        // Agrega una habilidad nueva
        btnHabilidad.addActionListener(e -> {
            String h = txtHabilidad.getText().trim();
            if (!h.isEmpty()) {
                perfil.agregarHabilidad(h);
                txtHabilidad.setText("");
                JOptionPane.showMessageDialog(this, "Habilidad agregada: " + h);
            }
        });

        setVisible(true);
    }
}