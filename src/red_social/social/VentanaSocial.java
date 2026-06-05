/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social, Eventos y Reportes
 * CLASE: VentanaSocial  (ventana principal del modulo 3)
 * AUTOR: [juan silva]
 * FECHA: 2025
 */

package red_social.social;

import red_social.eventos.Evento;
import red_social.persistencia.PersistenciaEventos;
import red_social.persistencia.PersistenciaSocial;
import red_social.usuarios.GestorUsuarios;
import red_social.usuarios.Perfil;
import red_social.usuarios.Profesor;
import red_social.usuarios.Administrador;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * Ventana principal del modulo social.
 * Se abre despues del login. Recibe el Perfil del usuario en sesion
 * y el GestorUsuarios compartido con el modulo 1.
 *
 * Pestanas:
 *   1. Foro General     → todas las publicaciones, busqueda por codigo
 *   2. Eventos          → calendario de eventos por tipo
 *   3. Tutorias         → solo Profesores y Admins
 *   4. Moderacion       → solo Admins
 */
public class VentanaSocial extends JFrame {

    // ── Paleta de colores ─────────────────────────────────────────
    private static final Color AZUL_UD     = new Color(0, 51, 102);
    private static final Color AZUL_CLARO  = new Color(0, 102, 179);
    private static final Color DORADO      = new Color(204, 153, 0);
    private static final Color FONDO       = new Color(245, 247, 250);
    private static final Color TARJETA     = Color.WHITE;
    private static final Color TEXTO_GRIS  = new Color(90, 90, 110);
    private static final Color VERDE_LIKE  = new Color(39, 174, 96);
    private static final Color ROJO_MOD    = new Color(192, 57, 43);

    // Fuentes
    private static final Font F_TITULO  = new Font("Georgia", Font.BOLD, 22);
    private static final Font F_SECCION = new Font("Georgia", Font.BOLD, 14);
    private static final Font F_NORMAL  = new Font("Tahoma", Font.PLAIN, 13);
    private static final Font F_CODIGO  = new Font("Courier New", Font.BOLD, 12);
    private static final Font F_SMALL   = new Font("Tahoma", Font.PLAIN, 11);

    // ── Estado ────────────────────────────────────────────────────
    private final Perfil         usuarioSesion;
    private final GestorUsuarios gestorUsuarios;
    private final Feed           feed;
    private final ArrayList<Evento> eventos;
    private final PersistenciaSocial  persSocial;
    private final PersistenciaEventos persEventos;

    // Componentes reutilizados
    private JPanel      panelFeed;
    private JTextField  campoBusquedaCodigo;
    private JComboBox<String> comboFiltroArea;

    // ── Constructor ───────────────────────────────────────────────

    public VentanaSocial(Perfil usuarioSesion, GestorUsuarios gestorUsuarios,
                         Feed feed, ArrayList<Evento> eventos,
                         PersistenciaSocial persSocial, PersistenciaEventos persEventos) {
        this.usuarioSesion  = usuarioSesion;
        this.gestorUsuarios = gestorUsuarios;
        this.feed           = feed;
        this.eventos        = eventos;
        this.persSocial     = persSocial;
        this.persEventos    = persEventos;

        configurarVentana();
        construirUI();
    }

    // ── Configuracion de ventana ──────────────────────────────────

    private void configurarVentana() {
        setTitle("Red Social Academica — UD");
        setSize(1100, 720);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(FONDO);

        // Al cerrar, guardar datos
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarYCerrar();
            }
        });

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
    }

    // ── Construccion de la UI ─────────────────────────────────────

    private void construirUI() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 0));
        contenedor.setBackground(FONDO);

        contenedor.add(construirHeader(), BorderLayout.NORTH);
        contenedor.add(construirPestanas(), BorderLayout.CENTER);

        setContentPane(contenedor);
        setVisible(true);
    }

    // ─────────────────────────────────────────────────────────────
    //  HEADER
    // ─────────────────────────────────────────────────────────────

    private JPanel construirHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AZUL_UD);
        header.setBorder(new EmptyBorder(14, 22, 14, 22));

        // Izquierda: titulo + subtitulo
        JPanel izq = new JPanel();
        izq.setOpaque(false);
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("🎓 Red Social Académica");
        lblTitulo.setFont(F_TITULO);
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblUD = new JLabel("Universidad Distrital Francisco José de Caldas");
        lblUD.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblUD.setForeground(new Color(180, 200, 230));

        izq.add(lblTitulo);
        izq.add(Box.createVerticalStrut(2));
        izq.add(lblUD);

        // Derecha: chip de usuario + cerrar sesion
        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        der.setOpaque(false);

        String rolEmoji = (usuarioSesion instanceof Administrador) ? "🛡️" :
                          (usuarioSesion instanceof Profesor)       ? "👨‍🏫" : "🎓";
        JLabel lblUsuario = new JLabel(rolEmoji + "  " +
                usuarioSesion.getNombre() + " " + usuarioSesion.getApellido());
        lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblUsuario.setForeground(DORADO);
        lblUsuario.setBorder(new EmptyBorder(4, 10, 4, 10));

        JButton btnCerrar = boton("Cerrar sesión", ROJO_MOD);
        btnCerrar.addActionListener(e -> guardarYCerrar());

        der.add(lblUsuario);
        der.add(btnCerrar);

        header.add(izq, BorderLayout.WEST);
        header.add(der, BorderLayout.EAST);
        return header;
    }

    // ─────────────────────────────────────────────────────────────
    //  PESTANAS
    // ─────────────────────────────────────────────────────────────

    private JTabbedPane construirPestanas() {
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.LEFT);
        tabs.setFont(new Font("Tahoma", Font.BOLD, 13));
        tabs.setBackground(FONDO);

        // ── Pestana 1: Foro General ───────────────────────────────
        tabs.addTab("📰  Foro General   ", panelForoGeneral());

        // ── Pestana 2: Eventos ────────────────────────────────────
        tabs.addTab("📅  Eventos        ", panelEventos());

        // ── Pestana 3: Tutorias (Profesor + Admin) ─────────────────
        if (usuarioSesion instanceof Profesor || usuarioSesion instanceof Administrador) {
            tabs.addTab("👨‍🏫  Tutorías       ", panelTutorias());
        }

        // ── Pestana 4: Moderacion (solo Admin) ────────────────────
        if (usuarioSesion instanceof Administrador) {
            tabs.addTab("🛡️  Moderación     ", panelModeracion());
        }

        return tabs;
    }

    // ─────────────────────────────────────────────────────────────
    //  PESTANA 1: FORO GENERAL
    // ─────────────────────────────────────────────────────────────

    private JPanel panelForoGeneral() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(FONDO);
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        // ── Barra superior: busqueda + filtro ─────────────────────
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        barra.setOpaque(false);

        campoBusquedaCodigo = new JTextField(10);
        campoBusquedaCodigo.setFont(F_CODIGO);
        campoBusquedaCodigo.setToolTipText("Busca por código ej: PUB-A");
        JButton btnBuscar = boton("🔍 Buscar", AZUL_CLARO);

        String[] areas = {"Todas las áreas","IA","Redes","Diseño","Software","Hardware","Investigación","Otro"};
        comboFiltroArea = new JComboBox<>(areas);
        comboFiltroArea.setFont(F_NORMAL);
        JButton btnFiltrar = boton("Filtrar", AZUL_UD);
        JButton btnTodo    = boton("Ver todo", TEXTO_GRIS);

        barra.add(new JLabel("Código:"));
        barra.add(campoBusquedaCodigo);
        barra.add(btnBuscar);
        barra.add(Box.createHorizontalStrut(20));
        barra.add(new JLabel("Área:"));
        barra.add(comboFiltroArea);
        barra.add(btnFiltrar);
        barra.add(btnTodo);

        // ── Centro: scroll con tarjetas ───────────────────────────
        panelFeed = new JPanel();
        panelFeed.setLayout(new BoxLayout(panelFeed, BoxLayout.Y_AXIS));
        panelFeed.setBackground(FONDO);

        JScrollPane scroll = new JScrollPane(panelFeed);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // ── Sur: caja para nueva publicacion ─────────────────────
        JPanel cajaPublicar = construirCajaPublicar();

        panel.add(barra, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(cajaPublicar, BorderLayout.SOUTH);

        // ── Eventos de botones ────────────────────────────────────
        btnBuscar.addActionListener(e -> {
            String cod = campoBusquedaCodigo.getText().trim().toUpperCase();
            if (cod.isEmpty()) { mostrarPublicaciones(feed.getPublicaciones()); return; }
            Publicacion pub = feed.buscarPorCodigo(cod);
            ArrayList<Publicacion> res = new ArrayList<>();
            if (pub != null) res.add(pub);
            mostrarPublicaciones(res);
            if (pub == null) JOptionPane.showMessageDialog(this,
                    "No se encontró ninguna publicación con código: " + cod,
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        });

        btnFiltrar.addActionListener(e -> {
            String area = (String) comboFiltroArea.getSelectedItem();
            if (area == null || area.startsWith("Todas")) {
                mostrarPublicaciones(feed.getPublicaciones());
            } else {
                mostrarPublicaciones(feed.filtrarPorArea(area));
            }
        });

        btnTodo.addActionListener(e -> mostrarPublicaciones(feed.getPublicaciones()));

        // Carga inicial
        mostrarPublicaciones(feed.getPublicaciones());

        return panel;
    }

    private JPanel construirCajaPublicar() {
        JPanel caja = new JPanel(new BorderLayout(8, 6));
        caja.setBackground(TARJETA);
        caja.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 0, 0, 0, new Color(210,215,225)),
            new EmptyBorder(10, 14, 10, 14)
        ));

        JLabel lblNueva = new JLabel("✍  Nueva publicación");
        lblNueva.setFont(F_SECCION);
        lblNueva.setForeground(AZUL_UD);

        JTextArea campoTexto = new JTextArea(3, 40);
        campoTexto.setFont(F_NORMAL);
        campoTexto.setLineWrap(true);
        campoTexto.setWrapStyleWord(true);
        campoTexto.setBorder(new EmptyBorder(6, 8, 6, 8));
        campoTexto.setBackground(FONDO);

        String[] areas = {"General","IA","Redes","Diseño","Software","Hardware","Investigación","Otro"};
        JComboBox<String> comboArea = new JComboBox<>(areas);
        comboArea.setFont(F_NORMAL);

        JButton btnPublicar = boton("Publicar", AZUL_CLARO);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        controles.setOpaque(false);
        controles.add(new JLabel("Área:"));
        controles.add(comboArea);
        controles.add(btnPublicar);

        caja.add(lblNueva, BorderLayout.NORTH);
        caja.add(new JScrollPane(campoTexto), BorderLayout.CENTER);
        caja.add(controles, BorderLayout.SOUTH);

        btnPublicar.addActionListener(e -> {
            String texto = campoTexto.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El contenido no puede estar vacío.");
                return;
            }
            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            String area  = (String) comboArea.getSelectedItem();
            Publicacion pub = new Publicacion(usuarioSesion, texto, fecha, area);
            feed.agregarPublicacion(pub);
            campoTexto.setText("");
            guardarSocial();
            mostrarPublicaciones(feed.getPublicaciones());
            JOptionPane.showMessageDialog(this,
                    "Publicación creada  ·  Código: " + pub.getCodigo(),
                    "¡Publicado!", JOptionPane.INFORMATION_MESSAGE);
        });

        return caja;
    }

    // Renderiza tarjetas de publicaciones en el panelFeed
    private void mostrarPublicaciones(ArrayList<Publicacion> lista) {
        panelFeed.removeAll();
        if (lista.isEmpty()) {
            JLabel vacio = new JLabel("  No hay publicaciones para mostrar.");
            vacio.setFont(F_NORMAL);
            vacio.setForeground(TEXTO_GRIS);
            vacio.setBorder(new EmptyBorder(20, 0, 0, 0));
            panelFeed.add(vacio);
        } else {
            for (Publicacion p : lista) {
                panelFeed.add(tarjetaPublicacion(p));
                panelFeed.add(Box.createVerticalStrut(8));
            }
        }
        panelFeed.revalidate();
        panelFeed.repaint();
    }

    private JPanel tarjetaPublicacion(Publicacion pub) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(TARJETA);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 225, 235), 1, true),
            new EmptyBorder(12, 16, 12, 16)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // ── Cabecera de la tarjeta ────────────────────────────────
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);

        String autorNombre = (pub.getAutor() != null)
            ? pub.getAutor().getNombre() + " " + pub.getAutor().getApellido()
            : "Usuario desconocido";
        JLabel lblAutor = new JLabel(autorNombre);
        lblAutor.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblAutor.setForeground(AZUL_UD);

        JLabel lblMeta = new JLabel(pub.getCodigo() + "  ·  " + pub.getArea() + "  ·  " + pub.getFecha());
        lblMeta.setFont(F_SMALL);
        lblMeta.setForeground(TEXTO_GRIS);

        JPanel infoAutor = new JPanel();
        infoAutor.setOpaque(false);
        infoAutor.setLayout(new BoxLayout(infoAutor, BoxLayout.Y_AXIS));
        infoAutor.add(lblAutor);
        infoAutor.add(lblMeta);

        cabecera.add(infoAutor, BorderLayout.WEST);

        // ── Contenido ─────────────────────────────────────────────
        JTextArea txtContenido = new JTextArea(pub.getContenido());
        txtContenido.setEditable(false);
        txtContenido.setFont(F_NORMAL);
        txtContenido.setBackground(TARJETA);
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        txtContenido.setBorder(null);

        // ── Pie: likes + comentar + ver comentarios ────────────────
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pie.setOpaque(false);

        boolean yaDioLike = pub.tienelike(usuarioSesion);
        JButton btnLike = boton(
            (yaDioLike ? "❤️ " : "🤍 ") + pub.getCantidadLikes(),
            yaDioLike ? VERDE_LIKE : new Color(150,150,160)
        );
        btnLike.setFont(F_SMALL);

        JButton btnComentar = boton("💬 Comentar", AZUL_CLARO);
        btnComentar.setFont(F_SMALL);

        JButton btnVerCom = boton("Ver comentarios (" + pub.getCantidadComentarios() + ")", new Color(120,120,130));
        btnVerCom.setFont(F_SMALL);

        pie.add(btnLike);
        pie.add(btnComentar);
        pie.add(btnVerCom);

        card.add(cabecera,    BorderLayout.NORTH);
        card.add(txtContenido,BorderLayout.CENTER);
        card.add(pie,         BorderLayout.SOUTH);

        // ── Acciones ──────────────────────────────────────────────
        btnLike.addActionListener(e -> {
            if (pub.tienelike(usuarioSesion)) {
                pub.quitarLike(usuarioSesion);
            } else {
                pub.darLike(usuarioSesion);
            }
            guardarSocial();
            mostrarPublicaciones(feed.getPublicaciones());
        });

        btnComentar.addActionListener(e -> {
            String texto = JOptionPane.showInputDialog(this,
                    "Escribe tu comentario:", "Comentar", JOptionPane.PLAIN_MESSAGE);
            if (texto != null && !texto.trim().isEmpty()) {
                String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
                pub.agregarComentario(new Comentario(usuarioSesion, texto.trim(), fecha));
                guardarSocial();
                mostrarPublicaciones(feed.getPublicaciones());
            }
        });

        btnVerCom.addActionListener(e -> mostrarVentanaComentarios(pub));

        return card;
    }

    private void mostrarVentanaComentarios(Publicacion pub) {
        JDialog dlg = new JDialog(this, "Comentarios — " + pub.getCodigo(), true);
        dlg.setSize(500, 420);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(8, 8));

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(FONDO);

        for (Comentario c : pub.getComentarios()) {
            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(TARJETA);
            row.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220,225,235), 1),
                new EmptyBorder(8, 12, 8, 12)
            ));
            String nombre = (c.getAutor() != null)
                ? c.getAutor().getNombre() + " " + c.getAutor().getApellido()
                : "Anónimo";
            JLabel autor = new JLabel(nombre + "  ·  " + c.getFecha());
            autor.setFont(new Font("Tahoma", Font.BOLD, 11));
            autor.setForeground(AZUL_UD);
            JLabel texto = new JLabel("<html>" + c.getTexto().replace("\n","<br>") + "</html>");
            texto.setFont(F_NORMAL);
            row.add(autor, BorderLayout.NORTH);
            row.add(texto, BorderLayout.CENTER);
            lista.add(row);
            lista.add(Box.createVerticalStrut(4));
        }

        if (pub.getComentarios().isEmpty()) {
            JLabel vacio = new JLabel("  Sin comentarios aún.");
            vacio.setFont(F_NORMAL);
            vacio.setForeground(TEXTO_GRIS);
            lista.add(vacio);
        }

        dlg.add(new JScrollPane(lista), BorderLayout.CENTER);
        JButton cerrar = boton("Cerrar", AZUL_UD);
        cerrar.addActionListener(e2 -> dlg.dispose());
        JPanel sur = new JPanel();
        sur.add(cerrar);
        dlg.add(sur, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    // ─────────────────────────────────────────────────────────────
    //  PESTANA 2: EVENTOS
    // ─────────────────────────────────────────────────────────────

    private JPanel panelEventos() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(FONDO);
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Filtro por tipo
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        barra.setOpaque(false);
        String[] tipos = {"Todos","ENTREGA","PRESENTACION","MENTORIA","REUNION"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        comboTipo.setFont(F_NORMAL);
        JButton btnFiltrar = boton("Filtrar", AZUL_UD);
        barra.add(new JLabel("Filtrar por tipo:"));
        barra.add(comboTipo);
        barra.add(btnFiltrar);

        // Lista de eventos
        JPanel listaEventos = new JPanel();
        listaEventos.setLayout(new BoxLayout(listaEventos, BoxLayout.Y_AXIS));
        listaEventos.setBackground(FONDO);

        JScrollPane scroll = new JScrollPane(listaEventos);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // Formulario nuevo evento (solo Profesor y Admin pueden crear)
        JPanel surPanel = new JPanel(new BorderLayout());
        surPanel.setOpaque(false);
        if (usuarioSesion instanceof Profesor || usuarioSesion instanceof Administrador) {
            surPanel.add(construirFormEvento(listaEventos, eventos), BorderLayout.CENTER);
        }

        panel.add(barra,    BorderLayout.NORTH);
        panel.add(scroll,   BorderLayout.CENTER);
        panel.add(surPanel, BorderLayout.SOUTH);

        // Render inicial
        renderizarEventos(listaEventos, eventos);

        btnFiltrar.addActionListener(e -> {
            String tipo = (String) comboTipo.getSelectedItem();
            if (tipo == null || tipo.equals("Todos")) {
                renderizarEventos(listaEventos, eventos);
            } else {
                ArrayList<Evento> filtrados = new ArrayList<>();
                for (Evento ev : eventos) {
                    if (ev.getTipo().equalsIgnoreCase(tipo)) filtrados.add(ev);
                }
                renderizarEventos(listaEventos, filtrados);
            }
        });

        return panel;
    }

    private void renderizarEventos(JPanel contenedor, ArrayList<Evento> lista) {
        contenedor.removeAll();
        String[] emojis = {"ENTREGA","📦","PRESENTACION","🎤","MENTORIA","👨‍🏫","REUNION","📅"};
        if (lista.isEmpty()) {
            JLabel v = new JLabel("  No hay eventos registrados.");
            v.setFont(F_NORMAL); v.setForeground(TEXTO_GRIS);
            v.setBorder(new EmptyBorder(16,0,0,0));
            contenedor.add(v);
        } else {
            for (Evento ev : lista) {
                String emoji = "📌";
                for (int i = 0; i < emojis.length - 1; i += 2) {
                    if (ev.getTipo().equalsIgnoreCase(emojis[i])) { emoji = emojis[i+1]; break; }
                }
                JPanel card = new JPanel(new BorderLayout(0,4));
                card.setBackground(TARJETA);
                card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(220,225,235), 1, true),
                    new EmptyBorder(10, 14, 10, 14)
                ));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

                JLabel titulo = new JLabel(emoji + "  " + ev.getNombre() + "   [" + ev.getCodigo() + "]");
                titulo.setFont(F_SECCION); titulo.setForeground(AZUL_UD);

                JLabel meta = new JLabel(ev.getTipo() + "  ·  " + ev.getFecha() + "  ·  " + ev.getDescripcion());
                meta.setFont(F_SMALL); meta.setForeground(TEXTO_GRIS);

                card.add(titulo, BorderLayout.NORTH);
                card.add(meta,   BorderLayout.CENTER);
                contenedor.add(card);
                contenedor.add(Box.createVerticalStrut(6));
            }
        }
        contenedor.revalidate();
        contenedor.repaint();
    }

    private JPanel construirFormEvento(JPanel listaEventos, ArrayList<Evento> eventosList) {
        JPanel form = new JPanel(new BorderLayout(8, 6));
        form.setBackground(TARJETA);
        form.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 0, 0, 0, new Color(210,215,225)),
            new EmptyBorder(10, 14, 10, 14)
        ));

        JLabel lbl = new JLabel("📅  Nuevo evento");
        lbl.setFont(F_SECCION); lbl.setForeground(AZUL_UD);

        JPanel campos = new JPanel(new GridLayout(1, 4, 8, 0));
        campos.setOpaque(false);

        JTextField campoNombre = new JTextField(); campoNombre.setFont(F_NORMAL);
        JTextField campoFecha  = new JTextField("dd/mm/aaaa"); campoFecha.setFont(F_NORMAL);
        String[] tiposEvento   = {"ENTREGA","PRESENTACION","MENTORIA","REUNION"};
        JComboBox<String> comboTipoEv = new JComboBox<>(tiposEvento); comboTipoEv.setFont(F_NORMAL);
        JTextField campoDesc   = new JTextField(); campoDesc.setFont(F_NORMAL);

        campos.add(rotular("Nombre:", campoNombre));
        campos.add(rotular("Fecha:", campoFecha));
        campos.add(rotular("Tipo:", comboTipoEv));
        campos.add(rotular("Descripción:", campoDesc));

        JButton btnCrear = boton("Crear evento", DORADO);

        form.add(lbl,    BorderLayout.NORTH);
        form.add(campos, BorderLayout.CENTER);
        form.add(btnCrear, BorderLayout.EAST);

        btnCrear.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            String fecha  = campoFecha.getText().trim();
            String desc   = campoDesc.getText().trim();
            if (nombre.isEmpty() || fecha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y fecha son obligatorios.");
                return;
            }
            String tipo = (String) comboTipoEv.getSelectedItem();
         // Cambiamos el null final por ""
            Evento ev = new Evento(nombre, fecha, tipo, usuarioSesion.getCorreo(), desc, "");

            eventosList.add(ev);
            persEventos.guardarEventos(eventosList);
            renderizarEventos(listaEventos, eventosList);
            campoNombre.setText(""); campoFecha.setText("dd/mm/aaaa"); campoDesc.setText("");
            JOptionPane.showMessageDialog(this,
                "Evento creado  ·  Código: " + ev.getCodigo(),
                "¡Creado!", JOptionPane.INFORMATION_MESSAGE);
        });

        return form;
    }

    // ─────────────────────────────────────────────────────────────
    //  PESTANA 3: TUTORIAS (Profesor + Admin)
    // ─────────────────────────────────────────────────────────────

    private JPanel panelTutorias() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(FONDO);
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel titulo = new JLabel("👨‍🏫  Módulo de Tutorías y Retroalimentación");
        titulo.setFont(F_TITULO); titulo.setForeground(AZUL_UD);
        titulo.setBorder(new EmptyBorder(0, 0, 12, 0));

        // Busqueda de estudiante para dar retroalimentacion
        JPanel busqPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        busqPanel.setOpaque(false);
        JTextField campoCorreo = new JTextField(20); campoCorreo.setFont(F_NORMAL);
        JButton btnBuscar = boton("Buscar estudiante", AZUL_CLARO);
        busqPanel.add(new JLabel("Correo del estudiante:"));
        busqPanel.add(campoCorreo);
        busqPanel.add(btnBuscar);

        // Area de retroalimentacion
        JPanel areaRetro = new JPanel(new BorderLayout(0, 6));
        areaRetro.setBackground(TARJETA);
        areaRetro.setBorder(new EmptyBorder(12, 14, 12, 14));
        areaRetro.setVisible(false);

        JLabel lblEstInfo = new JLabel("Estudiante:");
        lblEstInfo.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblEstInfo.setForeground(AZUL_UD);

        JTextArea campoRetro = new JTextArea(5, 40);
        campoRetro.setFont(F_NORMAL); campoRetro.setLineWrap(true); campoRetro.setWrapStyleWord(true);
        campoRetro.setBorder(new EmptyBorder(6, 8, 6, 8));

        JButton btnEnviar = boton("Publicar retroalimentación en el feed", AZUL_CLARO);

        areaRetro.add(lblEstInfo, BorderLayout.NORTH);
        areaRetro.add(new JScrollPane(campoRetro), BorderLayout.CENTER);
        areaRetro.add(btnEnviar, BorderLayout.SOUTH);

        JPanel norte = new JPanel(new BorderLayout(0, 10));
        norte.setOpaque(false);
        norte.add(titulo,    BorderLayout.NORTH);
        norte.add(busqPanel, BorderLayout.CENTER);

        panel.add(norte,    BorderLayout.NORTH);
        panel.add(areaRetro,BorderLayout.CENTER);

        // ── Acciones ──────────────────────────────────────────────
        final Perfil[] estudianteRef = {null};

        btnBuscar.addActionListener(e -> {
            String correo = campoCorreo.getText().trim();
            if (correo.isEmpty()) return;
            Perfil est = gestorUsuarios.buscarPorCorreo(correo);
            if (est == null) {
                JOptionPane.showMessageDialog(this, "No se encontró usuario con ese correo.");
                areaRetro.setVisible(false);
            } else {
                estudianteRef[0] = est;
                lblEstInfo.setText("Estudiante: " + est.getNombre() + " " + est.getApellido()
                        + "  ·  " + est.getCorreo());
                areaRetro.setVisible(true);
                areaRetro.revalidate();
            }
        });

        btnEnviar.addActionListener(e -> {
            if (estudianteRef[0] == null) return;
            String texto = campoRetro.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escribe la retroalimentación antes de publicar.");
                return;
            }
            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            String contenido = "📝 Retroalimentación para @" +
                    estudianteRef[0].getNombre() + " " + estudianteRef[0].getApellido() +
                    ":\n" + texto;
            Publicacion pub = new Publicacion(usuarioSesion, contenido, fecha, "Tutoría");
            feed.agregarPublicacion(pub);
            guardarSocial();
            mostrarPublicaciones(feed.getPublicaciones());
            campoRetro.setText("");
            JOptionPane.showMessageDialog(this,
                "Retroalimentación publicada  ·  Código: " + pub.getCodigo(),
                "¡Publicado!", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }

    // ─────────────────────────────────────────────────────────────
    //  PESTANA 4: MODERACION (solo Admin)
    // ─────────────────────────────────────────────────────────────

    private JPanel panelModeracion() {
        JPanel panel = new JPanel(new BorderLayout(8, 12));
        panel.setBackground(FONDO);
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel titulo = new JLabel("🛡️  Moderación y Estadísticas Globales");
        titulo.setFont(F_TITULO); titulo.setForeground(AZUL_UD);
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));

        // ── Seccion: eliminar publicacion ─────────────────────────
        JPanel secEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        secEliminar.setBackground(TARJETA);
        secEliminar.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ROJO_MOD, 1, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        JTextField campoCodMod = new JTextField(12); campoCodMod.setFont(F_CODIGO);
        JButton btnEliminar = boton("🗑️ Eliminar publicación", ROJO_MOD);
        secEliminar.add(new JLabel("Código a eliminar:"));
        secEliminar.add(campoCodMod);
        secEliminar.add(btnEliminar);

        // ── Seccion: estadisticas ─────────────────────────────────
        JPanel secStats = new JPanel(new BorderLayout(0, 8));
        secStats.setBackground(TARJETA);
        secStats.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210,215,225), 1, true),
            new EmptyBorder(14, 14, 14, 14)
        ));

        JLabel lblStats = new JLabel("📊 Estadísticas del sistema");
        lblStats.setFont(F_SECCION); lblStats.setForeground(AZUL_UD);

        JTextArea areaStats = new JTextArea();
        areaStats.setEditable(false);
        areaStats.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaStats.setBackground(new Color(248, 249, 252));

        JButton btnRefrescar = boton("↻ Actualizar estadísticas", AZUL_UD);

        secStats.add(lblStats,    BorderLayout.NORTH);
        secStats.add(new JScrollPane(areaStats), BorderLayout.CENTER);
        secStats.add(btnRefrescar, BorderLayout.SOUTH);

        // ── Seccion: listado de usuarios ──────────────────────────
        JPanel secUsuarios = new JPanel(new BorderLayout(0, 6));
        secUsuarios.setBackground(TARJETA);
        secUsuarios.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210,215,225), 1, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        JLabel lblUsu = new JLabel("👥 Usuarios del sistema");
        lblUsu.setFont(F_SECCION); lblUsu.setForeground(AZUL_UD);

        DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
        JList<String> listaUsuarios = new JList<>(modeloUsuarios);
        listaUsuarios.setFont(F_SMALL);
        for (Perfil p : gestorUsuarios.getUsuarios()) {
            String tipo = (p instanceof Administrador) ? "[ADMIN]" :
                          (p instanceof Profesor)       ? "[PROF]"  : "[EST]";
            modeloUsuarios.addElement(tipo + " " + p.getNombre() + " " + p.getApellido()
                    + "  ·  " + p.getCorreo());
        }
        secUsuarios.add(lblUsu, BorderLayout.NORTH);
        secUsuarios.add(new JScrollPane(listaUsuarios), BorderLayout.CENTER);

        // ── Layout general ────────────────────────────────────────
        JPanel centro = new JPanel(new GridLayout(1, 2, 12, 0));
        centro.setOpaque(false);
        centro.add(secStats);
        centro.add(secUsuarios);

        panel.add(titulo,     BorderLayout.NORTH);
        panel.add(secEliminar,BorderLayout.SOUTH);
        panel.add(centro,     BorderLayout.CENTER);

        // ── Acciones ──────────────────────────────────────────────
        Runnable actualizarStats = () -> {
            int totalPubl = feed.getTotalPublicaciones();
            int totalEv   = eventos.size();
            int totalUsu  = gestorUsuarios.getUsuarios().size();
            int estudiantes=0, profesores=0, admins=0, totalLikes=0, totalCom=0;
            for (Perfil p : gestorUsuarios.getUsuarios()) {
                if (p instanceof Administrador) admins++;
                else if (p instanceof Profesor) profesores++;
                else estudiantes++;
            }
            for (Publicacion pu : feed.getPublicaciones()) {
                totalLikes += pu.getCantidadLikes();
                totalCom   += pu.getCantidadComentarios();
            }
            areaStats.setText(
                "Publicaciones totales : " + totalPubl + "\n" +
                "Comentarios totales   : " + totalCom  + "\n" +
                "Likes totales         : " + totalLikes + "\n" +
                "Eventos totales       : " + totalEv   + "\n" +
                "─────────────────────────\n" +
                "Usuarios totales      : " + totalUsu  + "\n" +
                "  Estudiantes         : " + estudiantes + "\n" +
                "  Profesores          : " + profesores  + "\n" +
                "  Administradores     : " + admins
            );
        };
        actualizarStats.run();

        btnRefrescar.addActionListener(e -> actualizarStats.run());

        btnEliminar.addActionListener(e -> {
            String cod = campoCodMod.getText().trim().toUpperCase();
            if (cod.isEmpty()) return;
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar la publicación " + cod + "? Esta acción no se puede deshacer.",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = feed.eliminarPorCodigo(cod);
                if (ok) {
                    guardarSocial();
                    mostrarPublicaciones(feed.getPublicaciones());
                    JOptionPane.showMessageDialog(this, "Publicación eliminada.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró: " + cod);
                }
                campoCodMod.setText("");
                actualizarStats.run();
            }
        });

        return panel;
    }

    // ─────────────────────────────────────────────────────────────
    //  HELPERS
    // ─────────────────────────────────────────────────────────────

    private void guardarSocial() {
        persSocial.guardarFeed(feed);
    }

    private void guardarYCerrar() {
        guardarSocial();
        persEventos.guardarEventos(eventos);
        dispose();
        System.exit(0);
    }

    private JButton boton(String texto, Color fondo) {
        JButton btn = new JButton(texto);
        btn.setBackground(fondo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(7, 14, 7, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    private JPanel rotular(String etiqueta, JComponent campo) {
        JPanel p = new JPanel(new BorderLayout(0, 2));
        p.setOpaque(false);
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(F_SMALL); lbl.setForeground(TEXTO_GRIS);
        p.add(lbl, BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);
        return p;
    }
}
