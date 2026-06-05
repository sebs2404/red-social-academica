package red_social.gui;

import red_social.gestores.GestorProyectos;
import red_social.proyectos.*;
import red_social.usuarios.Perfil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.UUID;

/**
 * VentanaProyectos — GUI completa del Módulo 2: Proyectos y Equipos.
 *
 * Funcionalidades:
 *   • Crear / editar / eliminar proyectos
 *   • Buscar por ID, área o estado
 *   • Ver detalle completo (equipo, avances, invitaciones)
 *   • Registrar avances en un proyecto
 *   • Invitar colaboradores
 *
 * Depende de:
 *   GestorProyectos  — toda la lógica de negocio
 *   Proyecto         — modelo (id, nombre, area, descripcion, estado, lider,
 *                      equipo, avances, invitaciones)
 *   EstadoProyecto   — enum: BORRADOR, ACTIVO, CERRADO
 */
public class VentanaProyectos extends JFrame {

    // ── Estado ────────────────────────────────────────────────────────────────
    private final GestorProyectos gestorProyectos;
    private final Perfil          usuarioSesion;

    // ── Tabla principal ───────────────────────────────────────────────────────
    private DefaultTableModel modeloTabla;
    private JTable            tablaProyectos;

    // ── Campos del formulario ─────────────────────────────────────────────────
    private JTextField   txtId;
    private JTextField   txtNombre;
    private JTextField   txtArea;
    private JTextArea    txtDescripcion;
    private JComboBox<EstadoProyecto> cmbEstado;

    // ── Búsqueda ──────────────────────────────────────────────────────────────
    private JTextField              txtBusqueda;
    private JComboBox<String>       cmbCriterioBusqueda;

    // ─────────────────────────────────────────────────────────────────────────

    public VentanaProyectos(GestorProyectos gestorProyectos, Perfil usuarioSesion) {
        this.gestorProyectos = gestorProyectos;
        this.usuarioSesion   = usuarioSesion;
        inicializarUI();
        cargarTodosLosProyectos();
    }

    // ── Construcción de la UI ─────────────────────────────────────────────────

    private void inicializarUI() {
        setTitle("Módulo de Proyectos y Equipos — " + usuarioSesion.getNombre());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        add(crearPanelBusqueda(),   BorderLayout.NORTH);
        add(crearPanelCentral(),    BorderLayout.CENTER);
        add(crearPanelAcciones(),   BorderLayout.SOUTH);

        setSize(900, 620);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── Panel de búsqueda ─────────────────────────────────────────────────────

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Buscar proyectos"));

        cmbCriterioBusqueda = new JComboBox<>(new String[]{"Todos", "Por ID", "Por Área", "Por Estado"});
        txtBusqueda         = new JTextField(18);
        JButton btnBuscar   = new JButton("Buscar");
        JButton btnRefresh  = new JButton("↺ Todos");

        panel.add(new JLabel("Criterio:"));
        panel.add(cmbCriterioBusqueda);
        panel.add(new JLabel("Valor:"));
        panel.add(txtBusqueda);
        panel.add(btnBuscar);
        panel.add(btnRefresh);

        btnBuscar.addActionListener(e  -> buscar());
        btnRefresh.addActionListener(e -> cargarTodosLosProyectos());
        // Ocultar campo de texto si el criterio es "Todos"
        cmbCriterioBusqueda.addActionListener(e -> {
            boolean necesitaValor = !cmbCriterioBusqueda.getSelectedItem().equals("Todos");
            txtBusqueda.setEnabled(necesitaValor);
        });

        return panel;
    }

    // ── Panel central: tabla + formulario ─────────────────────────────────────

    private JSplitPane crearPanelCentral() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                crearPanelTabla(), crearPanelFormulario());
        split.setDividerLocation(500);
        split.setResizeWeight(0.6);
        return split;
    }

    private JScrollPane crearPanelTabla() {
        String[] columnas = {"ID", "Nombre", "Área", "Estado", "Líder"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaProyectos = new JTable(modeloTabla);
        tablaProyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProyectos.getTableHeader().setReorderingAllowed(false);

        // Al hacer clic → cargar datos en el formulario
        tablaProyectos.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int fila = tablaProyectos.getSelectedRow();
                if (fila >= 0) cargarEnFormulario(fila);
                if (e.getClickCount() == 2) verDetalle();
            }
        });

        JScrollPane scroll = new JScrollPane(tablaProyectos);
        scroll.setBorder(BorderFactory.createTitledBorder("Proyectos"));
        return scroll;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del proyecto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        txtId          = new JTextField(14);
        txtNombre      = new JTextField(14);
        txtArea        = new JTextField(14);
        txtDescripcion = new JTextArea(4, 14);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        cmbEstado      = new JComboBox<>(EstadoProyecto.values());

        txtId.setEditable(false); // generado automáticamente al crear
        txtId.setBackground(Color.LIGHT_GRAY);

        int fila = 0;
        agregarFila(panel, gbc, fila++, "ID:",          txtId);
        agregarFila(panel, gbc, fila++, "Nombre:",      txtNombre);
        agregarFila(panel, gbc, fila++, "Área:",        txtArea);
        agregarFila(panel, gbc, fila++, "Estado:",      cmbEstado);

        // Descripción ocupa más espacio
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 1;
        panel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panel.add(new JScrollPane(txtDescripcion), gbc);
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        return panel;
    }

    private void agregarFila(JPanel panel, GridBagConstraints gbc,
                              int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 1;
        panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(campo, gbc);
    }

    // ── Panel de botones de acción ────────────────────────────────────────────

    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));

        JButton btnNuevo      = new JButton("➕ Nuevo");
        JButton btnGuardar    = new JButton("💾 Guardar");
        JButton btnEliminar   = new JButton("🗑 Eliminar");
        JButton btnDetalle    = new JButton("🔍 Ver detalle");
        JButton btnAvance     = new JButton("📌 Registrar avance");
        JButton btnInvitar    = new JButton("📨 Invitar miembro");
        JButton btnLimpiar    = new JButton("✖ Limpiar");

        panel.add(btnNuevo);
        panel.add(btnGuardar);
        panel.add(btnEliminar);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(btnDetalle);
        panel.add(btnAvance);
        panel.add(btnInvitar);
        panel.add(btnLimpiar);

        btnNuevo.addActionListener(e    -> prepararNuevo());
        btnGuardar.addActionListener(e  -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        btnDetalle.addActionListener(e  -> verDetalle());
        btnAvance.addActionListener(e   -> registrarAvance());
        btnInvitar.addActionListener(e  -> invitarMiembro());
        btnLimpiar.addActionListener(e  -> limpiarFormulario());

        return panel;
    }

    // ── Operaciones principales ───────────────────────────────────────────────

    /** Carga todos los proyectos del gestor en la tabla. */
    private void cargarTodosLosProyectos() {
        modeloTabla.setRowCount(0);
        ArrayList<Proyecto> todos = gestorProyectos.listarTodos();
        for (Proyecto p : todos) {
            agregarFilaTabla(p);
        }
        limpiarFormulario();
    }

    private void agregarFilaTabla(Proyecto p) {
        String liderNombre = p.getLider() != null ? p.getLider().getNombre() : "—";
        modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getArea(),
                p.getEstado().name(),
                liderNombre
        });
    }

    /** Rellena el formulario con los datos de la fila seleccionada. */
    private void cargarEnFormulario(int fila) {
        String id = (String) modeloTabla.getValueAt(fila, 0);
        Proyecto p = gestorProyectos.buscarPorId(id);
        if (p == null) return;

        txtId.setText(p.getId());
        txtNombre.setText(p.getNombre());
        txtArea.setText(p.getArea());
        txtDescripcion.setText(p.getDescripcion());
        cmbEstado.setSelectedItem(p.getEstado());
    }

    /** Prepara el formulario para crear un proyecto nuevo. */
    private void prepararNuevo() {
        limpiarFormulario();
        txtId.setText("(auto)");
        txtNombre.requestFocus();
    }

    /** Guarda (crea o actualiza) el proyecto según si el ID ya existe. */
    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String area   = txtArea.getText().trim();

        if (nombre.isEmpty() || area.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre y el área son obligatorios.", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idActual = txtId.getText().trim();
        boolean esNuevo = idActual.isEmpty() || idActual.equals("(auto)");

        if (esNuevo) {
            // Crear proyecto nuevo
            String nuevoId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            Proyecto nuevo = new Proyecto(
                    nuevoId,
                    nombre,
                    area,
                    txtDescripcion.getText().trim(),
                    (EstadoProyecto) cmbEstado.getSelectedItem(),
                    usuarioSesion
            );
            gestorProyectos.registrarProyecto(nuevo);
            JOptionPane.showMessageDialog(this,
                    "Proyecto creado con ID: " + nuevoId, "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Actualizar proyecto existente
            Proyecto p = gestorProyectos.buscarPorId(idActual);
            if (p == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró el proyecto con ID: " + idActual,
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            p.setNombre(nombre);
            p.setArea(area);
            p.setDescripcion(txtDescripcion.getText().trim());
            p.setEstado((EstadoProyecto) cmbEstado.getSelectedItem());
            gestorProyectos.actualizarProyecto(p);
            JOptionPane.showMessageDialog(this,
                    "Proyecto actualizado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        cargarTodosLosProyectos();
    }

    /** Elimina el proyecto seleccionado (pide confirmación). */
    private void eliminar() {
        int fila = tablaProyectos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un proyecto de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = (String) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el proyecto con ID " + id + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestorProyectos.eliminarProyecto(id);
            cargarTodosLosProyectos();
        }
    }

    /** Muestra un diálogo con el detalle completo del proyecto seleccionado. */
    private void verDetalle() {
        int fila = tablaProyectos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un proyecto primero.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id    = (String) modeloTabla.getValueAt(fila, 0);
        Proyecto p   = gestorProyectos.buscarPorId(id);
        if (p == null) return;

        // Construir texto de detalle
        StringBuilder sb = new StringBuilder();
        sb.append("═══ DETALLE DEL PROYECTO ═══\n\n");
        sb.append("ID          : ").append(p.getId()).append("\n");
        sb.append("Nombre      : ").append(p.getNombre()).append("\n");
        sb.append("Área        : ").append(p.getArea()).append("\n");
        sb.append("Estado      : ").append(p.getEstado()).append("\n");
        sb.append("Líder       : ").append(p.getLider() != null
                ? p.getLider().getNombre() : "—").append("\n");
        sb.append("Descripción : ").append(p.getDescripcion()).append("\n\n");

        // Equipo
        sb.append("── Equipo (").append(p.getEquipo().size()).append(" miembros) ──\n");
        for (Miembro m : p.getEquipo()) {
            sb.append("  • ").append(m.getPerfil().getNombre())
              .append(" [").append(m.getRol()).append("]\n");
        }

        // Avances
        sb.append("\n── Avances (").append(p.getAvances().size()).append(") ──\n");
        for (Avance a : p.getAvances()) {
            sb.append("  [").append(a.getFecha()).append("] ")
              .append(a.getDescripcion()).append("\n");
        }

        // Invitaciones
        sb.append("\n── Invitaciones pendientes (").append(p.getInvitaciones().size()).append(") ──\n");
        for (Invitacion inv : p.getInvitaciones()) {
            sb.append("  → ").append(inv.getDestinatario().getNombre())
              .append(" (").append(inv.getEstado()).append(")\n");
        }

        JTextArea area = new JTextArea(sb.toString(), 22, 50);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this,
                new JScrollPane(area),
                "Detalle: " + p.getNombre(),
                JOptionPane.PLAIN_MESSAGE);
    }

    /** Diálogo para registrar un avance en el proyecto seleccionado. */
    private void registrarAvance() {
        int fila = tablaProyectos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un proyecto primero.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id   = (String) modeloTabla.getValueAt(fila, 0);
        Proyecto p  = gestorProyectos.buscarPorId(id);
        if (p == null) return;

        JTextArea txtAvance = new JTextArea(5, 30);
        txtAvance.setLineWrap(true);
        txtAvance.setWrapStyleWord(true);

        int ok = JOptionPane.showConfirmDialog(this,
                new Object[]{"Descripción del avance:", new JScrollPane(txtAvance)},
                "Registrar avance — " + p.getNombre(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (ok == JOptionPane.OK_OPTION) {
            String descripcion = txtAvance.getText().trim();
            if (!descripcion.isEmpty()) {
                Avance avance = new Avance(descripcion, usuarioSesion);
                gestorProyectos.registrarAvance(id, avance);
                JOptionPane.showMessageDialog(this,
                        "Avance registrado correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /** Diálogo para invitar a un usuario al proyecto seleccionado. */
    private void invitarMiembro() {
        int fila = tablaProyectos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un proyecto primero.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id  = (String) modeloTabla.getValueAt(fila, 0);
        Proyecto p = gestorProyectos.buscarPorId(id);
        if (p == null) return;

        String idInvitado = JOptionPane.showInputDialog(this,
                "ID o nombre del usuario a invitar:",
                "Invitar colaborador — " + p.getNombre(),
                JOptionPane.PLAIN_MESSAGE);

        if (idInvitado != null && !idInvitado.trim().isEmpty()) {
            boolean exito = gestorProyectos.invitarMiembro(id, idInvitado.trim());
            if (exito) {
                JOptionPane.showMessageDialog(this,
                        "Invitación enviada correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontró el usuario '" + idInvitado.trim() + "'.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** Búsqueda por el criterio seleccionado en el combo. */
    private void buscar() {
        String criterio = (String) cmbCriterioBusqueda.getSelectedItem();
        String valor    = txtBusqueda.getText().trim();
        modeloTabla.setRowCount(0);

        ArrayList<Proyecto> resultados = new ArrayList<>();

        switch (criterio) {
            case "Todos":
                resultados = gestorProyectos.listarTodos();
                break;
            case "Por ID":
                if (!valor.isEmpty()) {
                    Proyecto p = gestorProyectos.buscarPorId(valor);
                    if (p != null) resultados.add(p);
                }
                break;
            case "Por Área":
                if (!valor.isEmpty())
                    resultados = gestorProyectos.buscarPorArea(valor);
                break;
            case "Por Estado":
                try {
                    EstadoProyecto estado = EstadoProyecto.valueOf(valor.toUpperCase());
                    resultados = gestorProyectos.buscarPorEstado(estado);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Estado inválido. Usa: BORRADOR, ACTIVO o CERRADO.",
                            "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                break;
        }

        for (Proyecto p : resultados) agregarFilaTabla(p);

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron proyectos.", "Búsqueda",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Vacía todos los campos del formulario. */
    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtArea.setText("");
        txtDescripcion.setText("");
        cmbEstado.setSelectedIndex(0);
        tablaProyectos.clearSelection();
    }
}