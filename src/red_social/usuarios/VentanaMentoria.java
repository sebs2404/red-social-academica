/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Usuarios y Perfiles
 * CLASE: VentanaMentoria
 * AUTOR: Nicolas Vargas Chavarro
 * FECHA: 2025
 */

package red_social.usuarios;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Ventana donde profesores dan retroalimentacion a estudiantes
public class VentanaMentoria extends JFrame {

    private GestorUsuarios gestor;

    public VentanaMentoria(GestorUsuarios gestor) {
        this.gestor = gestor;

        setTitle("Modulo de Mentoria");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Lista de profesores disponibles
        JLabel lblTitulo = new JLabel("  Profesores disponibles para mentoria:", SwingConstants.LEFT);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaProfesores = new JList<>(modeloLista);

        // Carga profesores en la lista
        for (Perfil p : gestor.getUsuarios()) {
            if (p instanceof Profesor) {
                Profesor prof = (Profesor) p;
                modeloLista.addElement(prof.getNombre() + " " + prof.getApellido()
                        + " - " + prof.getDepartamento());
            }
        }

        JScrollPane scroll = new JScrollPane(listaProfesores);

        // Area para escribir retroalimentacion
        JTextArea txtRetroalimentacion = new JTextArea(5, 30);
        txtRetroalimentacion.setBorder(BorderFactory.createTitledBorder("Escribe tu consulta:"));
        JScrollPane scrollTexto = new JScrollPane(txtRetroalimentacion);

        JButton btnEnviar = new JButton("Enviar consulta");

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(scrollTexto, BorderLayout.CENTER);
        panelSur.add(btnEnviar, BorderLayout.SOUTH);

        add(lblTitulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        // Envia la consulta al profesor seleccionado
        btnEnviar.addActionListener(e -> {
            if (listaProfesores.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un profesor.");
                return;
            }
            if (txtRetroalimentacion.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escribe tu consulta.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Consulta enviada al profesor.");
            txtRetroalimentacion.setText("");
        });

        setVisible(true);
    }
}