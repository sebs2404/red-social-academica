package red_social.proyectos;

import red_social.usuarios.Perfil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaProyectos extends JFrame {

    private GestorProyectos gestor;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private Perfil usuario;

    public VentanaProyectos(GestorProyectos gestor, Perfil usuario) {

        this.gestor = gestor;
        this.usuario = usuario;

        setTitle("Gestion de Proyectos");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();

        JButton btnCrear = new JButton("Crear Proyecto");
        JButton btnEliminar = new JButton("Eliminar Proyecto");
        JButton btnBuscarArea = new JButton("Buscar por Area");
        JButton btnAvance = new JButton("Registrar Avance");
        JButton btnMetricas = new JButton("Metricas");

        panelBotones.add(btnCrear);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscarArea);
        panelBotones.add(btnAvance);
        panelBotones.add(btnMetricas);

        String[] columnas = {
                "ID",
                "Nombre",
                "Area",
                "Estado",
                "Lider"
        };

        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tabla);

        add(panelBotones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        actualizarTabla();

        btnCrear.addActionListener(e -> crearProyecto());
        btnEliminar.addActionListener(e -> eliminarProyecto());
        btnBuscarArea.addActionListener(e -> buscarPorArea());
        btnAvance.addActionListener(e -> registrarAvance());
        btnMetricas.addActionListener(e -> mostrarMetricas());

        setVisible(true);
    }

    private void actualizarTabla() {

        modeloTabla.setRowCount(0);

        for (Proyecto p : gestor.getProyectos()) {

            String lider = "";

            if (p.getLider() != null) {
                lider = p.getLider().getNombre();
            }

            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getArea(),
                    p.getEstado(),
                    lider
            });
        }
    }

    private void crearProyecto() {

        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtArea = new JTextField();
        JTextField txtDescripcion = new JTextField();

        Object[] campos = {
                "ID:", txtId,
                "Nombre:", txtNombre,
                "Area:", txtArea,
                "Descripcion:", txtDescripcion
        };

        int r = JOptionPane.showConfirmDialog(
                this,
                campos,
                "Crear Proyecto",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (r == JOptionPane.OK_OPTION) {

            Proyecto p = new Proyecto(
                    txtId.getText(),
                    txtNombre.getText(),
                    txtArea.getText(),
                    txtDescripcion.getText(),
                    usuario
            );

            gestor.registrarProyecto(p);

            actualizarTabla();

            JOptionPane.showMessageDialog(this,
                    "Proyecto registrado correctamente.");
        }
    }

    private void eliminarProyecto() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(this,
                    "Seleccione un proyecto.");
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);

        gestor.eliminarProyecto(id);

        actualizarTabla();

        JOptionPane.showMessageDialog(this,
                "Operacion realizada.");
    }

    private void buscarPorArea() {

        String area = JOptionPane.showInputDialog(
                this,
                "Area a buscar:"
        );

        if (area == null || area.trim().isEmpty()) {
            return;
        }

        modeloTabla.setRowCount(0);

        for (Proyecto p : gestor.buscarPorArea(area)) {

            String lider = "";

            if (p.getLider() != null) {
                lider = p.getLider().getNombre();
            }

            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getArea(),
                    p.getEstado(),
                    lider
            });
        }
    }

    private void registrarAvance() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(this,
                    "Seleccione un proyecto.");
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);

        Proyecto proyecto = gestor.buscarPorId(id);

        if (proyecto == null) {
            return;
        }

        JTextField txtDescripcion = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtAdjunto = new JTextField();

        Object[] campos = {
                "Descripcion:", txtDescripcion,
                "Fecha:", txtFecha,
                "Adjunto:", txtAdjunto
        };

        int r = JOptionPane.showConfirmDialog(
                this,
                campos,
                "Registrar Avance",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (r == JOptionPane.OK_OPTION) {

            proyecto.publicarAvance(
                    txtDescripcion.getText(),
                    txtFecha.getText(),
                    txtAdjunto.getText()
            );

            JOptionPane.showMessageDialog(this,
                    "Avance registrado.");
        }
    }

    private void mostrarMetricas() {

        int borradores = 0;
        int activos = 0;
        int cerrados = 0;

        for (Proyecto p : gestor.getProyectos()) {

            if (p.getEstado() == EstadoProyecto.BORRADOR) {
                borradores++;
            }

            if (p.getEstado() == EstadoProyecto.ACTIVO) {
                activos++;
            }

            if (p.getEstado() == EstadoProyecto.CERRADO) {
                cerrados++;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Total proyectos: " + gestor.getProyectos().size()
                        + "\nBorradores: " + borradores
                        + "\nActivos: " + activos
                        + "\nCerrados: " + cerrados
        );
    }
}