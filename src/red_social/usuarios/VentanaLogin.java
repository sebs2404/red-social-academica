/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Usuarios y Perfiles
 * CLASE: VentanaLogin
 * AUTOR: Nicolas Vargas Chavarro
 * FECHA: 2025
 */
 
package red_social.usuarios;
 
import red_social.RedSocialMain;
 
import javax.swing.*;
import java.awt.*;
 
// Ventana de inicio de sesion del sistema
public class VentanaLogin extends JFrame {
 
    private GestorUsuarios gestor;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
 
    public VentanaLogin(GestorUsuarios gestor) {
        this.gestor = gestor;
 
        setTitle("Red Social Academica - Login");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
 
        // Panel titulo
        JLabel lblTitulo = new JLabel("RED SOCIAL ACADEMICA - UD", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
 
        // Panel formulario
        JPanel panelForm = new JPanel(new GridLayout(2, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
 
        txtCorreo     = new JTextField();
        txtContrasena = new JPasswordField();
 
        panelForm.add(new JLabel("Correo:"));
        panelForm.add(txtCorreo);
        panelForm.add(new JLabel("Contrasena:"));
        panelForm.add(txtContrasena);
 
        // Panel botones
        JPanel panelBotones = new JPanel();
        JButton btnIngresar    = new JButton("Ingresar");
        JButton btnRegistrarse = new JButton("Registrarse");
 
        panelBotones.add(btnIngresar);
        panelBotones.add(btnRegistrarse);
 
        add(lblTitulo, BorderLayout.NORTH);
        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
 
        // Evento ingresar
        btnIngresar.addActionListener(e -> ingresar());
 
        // Evento registrarse - abre ventana de usuarios
        btnRegistrarse.addActionListener(e -> {
            new VentanaUsuarios(gestor);
        });
 
        setVisible(true);
    }
 
    // Verifica correo y contrasena
    private void ingresar() {
        String correo     = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();
 
        if (correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }
 
        Perfil p = gestor.buscarPorCorreo(correo);
 
        if (p != null && p.autenticar(correo, contrasena)) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + p.getNombre() + "!");
            dispose(); // cierra el login
            // Abre la ventana segun el tipo de usuario
            if (p instanceof Administrador) {
                new VentanaAdministrador(gestor);
            } else {
                // FIX: antes abria VentanaUsuarios (modulo de admin), lo cual
                // era incorrecto para usuarios normales. Ahora abre VentanaSocial
                // con todas las dependencias que maneja RedSocialMain.
                RedSocialMain.abrirVentanaSocial(p);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Correo o contrasena incorrectos.");
        }
    }
}