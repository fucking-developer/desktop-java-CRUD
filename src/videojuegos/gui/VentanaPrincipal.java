package videojuegos.gui;


/*
 * 
 * Jonathan Eduardo Ibarra Martínez.
 * 
 * Versión: 08/01/2023
 * 
 * Reto 2.
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.toedter.calendar.JDateChooser;

import videojuegos.basedatos.BaseDatosVideojuegos;
import videojuegos.dominio.Videojuego;
import videojuegos.excepcion.BaseDatosException;
import videojuegos.excepcion.VideojuegoExcepcion;
import videojuegos.libreria.MiFocusTraversalPolicy;

public class VentanaPrincipal extends JFrame implements WindowListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private JMenu archivo;
	private JMenuItem salir;
	private JMenu operaciones;
	private JMenuItem nuevo;
	private JMenuItem modificar;
	private JMenuItem guardar;
	private JMenuItem eliminar;
	private JMenuItem cancelar;
	private JMenuItem consultar;
	private JMenuBar barra;
	//propiedades
	private JTextField id;
	private JTextField nombre;
	private JTextField precio;
	private JSpinner peso;
	private JTextField idioma;
	private JRadioButton e;
	private JRadioButton t;
	private JRadioButton m;
	private JCheckBox accion;
	private JCheckBox aventura;
	private JCheckBox shooter;
	private JCheckBox rpg;
	private JCheckBox estrategia;
	private JCheckBox survival;
	private JCheckBox horror;
	private JCheckBox mundoAbierto;
	private JComboBox<String> estudiosDesarrollos;
	private JDateChooser fecha;
	private JComboBox<String> plataformasDisponibles;
	private JList<String> listaDeEstudios;
	
	private JButton agregar;
	private JButton quitar;
	private JLabel etiquetaImage;
	private JButton botonSeleccionarImagen;
	// Etiquetas
	private JLabel etiqueta;
	private JButton botonNuevo;
	private JButton botonModificar;
	private JButton botonGuardar;
	private JButton botonEliminar;
	private JButton botonCancelar;
	private JButton botonConsultar;
	private ImageIcon icono;
	private JComboBox<Videojuego> comboVideojuegos;
	// Coleccion de los objetos.
	private ArrayList<Videojuego> coleccionVideojuegos = new ArrayList<Videojuego>();
	// Bandera para validar cada que se crea un nuevo objeto.
	private boolean banderaNuevo = false;
	// Rutas de los archivos de texto con las opciones.
	private File rutaArchivoDeTextoEstudios = new File("estudios.txt");
	private File rutaArchivoDeTextoPlataformas = new File("plataformas.txt");
	// Modelo para añadir elementos al JList
	private DefaultListModel<String> modelo = new DefaultListModel<String>();
	// Imagen por defecto
	private File archivoImagen;
	private Videojuego baseDatosGames;

	public VentanaPrincipal() {
		try {
			// Valida si existe la base de datos
			if (!BaseDatosVideojuegos.validarBaseDatos()) {
				// Crea la base de datos
				BaseDatosVideojuegos.crearBaseDatos();
			}
		} catch (BaseDatosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error al crear la base de datos.",
					JOptionPane.ERROR_MESSAGE);
		}

		archivo = new JMenu("Archivo");
		archivo.setToolTipText("Contiene la opción salir.");
		archivo.setIcon(new ImageIcon(getClass().getResource("/videojuegos/imagenes/archivo.png")));
		archivo.setMnemonic(KeyEvent.VK_A);
		salir = new JMenuItem("Salir");
		salir.setToolTipText("Salir de la aplicación.");
		salir.setIcon(new ImageIcon(getClass().getResource("/videojuegos/imagenes/salir.png")));
		salir.setMnemonic(KeyEvent.VK_S);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		salir.addActionListener(this);

		archivo.add(salir);

		operaciones = new JMenu("Operaciones");
		operaciones.setToolTipText("Contiene las opciones nuevo, modificar, guardar, eliminar y cancelar.");
		operaciones.setIcon(new ImageIcon(getClass().getResource("/videojuegos/imagenes/operaciones.png")));
		operaciones.setMnemonic(KeyEvent.VK_O);
		nuevo = new JMenuItem("Nuevo");
		nuevo.setToolTipText("Crea una nuevo videojuego.");
		nuevo.setIcon(new ImageIcon(getClass().getResource("/videojuegos/imagenes/nuevo.png")));
		nuevo.setMnemonic(KeyEvent.VK_N);
		nuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		nuevo.addActionListener(this);

		modificar = new JMenuItem("Modificar");
		modificar.setToolTipText("Modifica el videojuego seleccionada.");
		modificar.setIcon(new ImageIcon(getClass().getResource("/videojuegos/imagenes/modificar.png")));
		modificar.setMnemonic(KeyEvent.VK_M);
		modificar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
		modificar.addActionListener(this);

		guardar = new JMenuItem("Guardar");
		guardar.setToolTipText("Guarda el videojuego.");
		guardar.setIcon(new ImageIcon(getClass().getResource("/videojuegos/imagenes/guardar.png")));
		guardar.setMnemonic(KeyEvent.VK_G);
		guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		guardar.addActionListener(this);

		eliminar = new JMenuItem("Eliminar");
		eliminar.setToolTipText("Elimina el videojuego seleccionado.");
		eliminar.setIcon(new ImageIcon(getClass().getResource("/videojuegos/imagenes/eliminar.png")));
		eliminar.setMnemonic(KeyEvent.VK_E);
		eliminar.addActionListener(this);

		cancelar = new JMenuItem("Cancelar");
		cancelar.setToolTipText("Cancela los datos ingresados.");
		cancelar.setIcon(new ImageIcon(getClass().getResource("/videojuegos/imagenes/cancelar.png")));
		cancelar.setMnemonic(KeyEvent.VK_C);
		cancelar.addActionListener(this);

		consultar = new JMenuItem("Consultar");
		consultar.setToolTipText("Consulta los videojuegos guardadas.");
		consultar.setIcon(new ImageIcon(getClass().getResource("/videojuegos/imagenes/consulta.png")));
		consultar.setMnemonic(KeyEvent.VK_R);
		consultar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		consultar.addActionListener(this);

		operaciones.add(nuevo);
		operaciones.add(modificar);
		operaciones.add(guardar);
		operaciones.add(eliminar);
		operaciones.add(cancelar);
		operaciones.add(consultar);

		barra = new JMenuBar();
		barra.add(archivo);
		barra.add(operaciones);
		this.setJMenuBar(barra);

		JPanel panel = new JPanel(null);

		etiqueta = new JLabel("Videojuegos: ");
		etiqueta.setBounds(410, 20, 150, 25);
		panel.add(etiqueta);

		// Obtiene los videojuegos en la base de datos y las almacena en la
		// colección de objetos.
		try {
			coleccionVideojuegos = BaseDatosVideojuegos.obtenerVideojuegos();
		} catch (ClassNotFoundException | SQLException | BaseDatosException e) {
			JOptionPane.showMessageDialog(null,
					"Ocurrio un error al  obtener los datos de la base de datos.\nFavor de contactar con soporte técnico.",
					"Error de lectura en la base de datos.", JOptionPane.ERROR_MESSAGE);
		}

		// Se agrega al combo principal los videojuegos de la colección.
		comboVideojuegos = new JComboBox<Videojuego>();
		for (int i = 0; i < coleccionVideojuegos.size(); i++) {
			comboVideojuegos.addItem(coleccionVideojuegos.get(i));
		}

		comboVideojuegos.addActionListener(this);
		comboVideojuegos.setBounds(500, 20, 240, 30);
		comboVideojuegos.setToolTipText("Listado de los videojuegos guardados.");
		panel.add(comboVideojuegos);

		etiqueta = new JLabel("Id del videojuego: ");
		etiqueta.setBounds(150, 70, 200, 25);
		panel.add(etiqueta);
		id = new JTextField();
		id.setToolTipText("Ingrese el id del videojuego.");
		id.setBounds(350, 70, 200, 25);
		panel.add(id);

		etiqueta = new JLabel("Nombre del videojuego: ");
		etiqueta.setBounds(150, 110, 200, 25);
		panel.add(etiqueta);
		nombre = new JTextField();
		nombre.setToolTipText("Ingrese el nombre del videojuego.");
		nombre.setBounds(350, 110, 200, 25);
		panel.add(nombre);

		etiqueta = new JLabel("Precio del videojuego: ");
		etiqueta.setBounds(150, 150, 200, 25);
		panel.add(etiqueta);
		precio = new JTextField("0.0");
		precio.setToolTipText("Ingrese el precio del videojuego.");
		precio.setBounds(350, 150, 200, 25);
		panel.add(precio);

		etiqueta = new JLabel("Idioma del videojuego: ");
		etiqueta.setBounds(150, 190, 200, 25);
		panel.add(etiqueta);
		idioma = new JTextField();
		idioma.setToolTipText("Ingrese el idioma del videojuego.");
		idioma.setBounds(350, 190, 200, 25);
		panel.add(idioma);

		etiqueta = new JLabel("Peso del videojuego (Gb): ");
		etiqueta.setBounds(150, 230, 200, 25);
		panel.add(etiqueta);
		peso = new JSpinner();
		peso.setToolTipText("Ingrese el peso del videojuego.");
		peso.setBounds(350, 230, 200, 25);
		panel.add(peso);

		etiqueta = new JLabel("Clasificación del videojuego: ");
		etiqueta.setBounds(150, 270, 200, 25);
		panel.add(etiqueta);
		e = new JRadioButton("E");
		e.setToolTipText("Para todo el publico.");
		t = new JRadioButton("T");
		t.setToolTipText("Para adolescentes.");
		m = new JRadioButton("M");
		m.setToolTipText("Para adultos.");
		ButtonGroup grupo = new ButtonGroup();
		grupo.add(e);
		grupo.add(t);
		grupo.add(m);
		e.setBounds(350, 270, 50, 25);
		panel.add(e);
		t.setBounds(400, 270, 60, 25);
		panel.add(t);
		m.setBounds(460, 270, 50, 25);
		panel.add(m);

		etiqueta = new JLabel("Categorias del videojuego: ");
		etiqueta.setBounds(150, 310, 250, 25);
		panel.add(etiqueta);

		accion = new JCheckBox("Acción");
		accion.setToolTipText("Este juego se enfoca en la acción, con combates emocionantes y desafíos intensos.");
		accion.setBounds(350, 310, 150, 25);
		panel.add(accion);

		aventura = new JCheckBox("Aventura");
		aventura.setToolTipText("Explora mundos fascinantes, resuelve acertijos y vive emocionantes historias en esta aventura.");
		aventura.setBounds(350, 335, 150, 25);
		panel.add(aventura);

		shooter = new JCheckBox("Shooter");
		shooter.setToolTipText("Sumérgete en intensos tiroteos y combates estratégicos en este juego de disparos.");
		shooter.setBounds(350, 360, 150, 25);
		panel.add(shooter);

		rpg = new JCheckBox("RPG");
		rpg.setToolTipText("Vive aventuras épicas, mejora a tu personaje y sumérgete en historias inmersivas de rol (RPG).");
		rpg.setBounds(350, 385, 200, 25);
		panel.add(rpg);

		estrategia = new JCheckBox("Estrategia");
		estrategia.setToolTipText("Planifica tácticas, construye y conquista territorios en este juego de estrategia.");
		estrategia.setBounds(350, 410, 200, 25);
		panel.add(estrategia);

		survival = new JCheckBox("Survival");
		survival.setToolTipText("Sobrevive a entornos hostiles, busca recursos y enfrenta desafíos extremos en este juego de supervivencia.");
		survival.setBounds(350, 435, 150, 25);
		panel.add(survival);

		horror = new JCheckBox("Horror");
		horror.setToolTipText("Experimenta el miedo y la tensión en un ambiente de horror y suspenso.");
		horror.setBounds(350, 460, 250, 25);
		panel.add(horror);

		mundoAbierto = new JCheckBox("Mundo abierto");
		mundoAbierto.setToolTipText("Explora un vasto mundo lleno de posibilidades y aventuras en este juego de mundo abierto.");
		mundoAbierto.setBounds(350, 485, 300, 25);
		panel.add(mundoAbierto);

		etiqueta = new JLabel("Estudio de desarrollo: ");
		etiqueta.setBounds(600, 70, 250, 25);
		panel.add(etiqueta);
		estudiosDesarrollos = new JComboBox<String>();
		estudiosDesarrollos.setToolTipText("Seleccione un estudio de desarrollo.");
		estudiosDesarrollos.setBounds(800, 70, 200, 25);
		panel.add(estudiosDesarrollos);

		etiqueta = new JLabel("Fecha de lanzamiento: ");
		etiqueta.setBounds(600, 120, 250, 25);
		panel.add(etiqueta);
		fecha = new JDateChooser(new Date());
		fecha.setToolTipText("Seleccione la fecha de lanzamiento.");
		fecha.setBounds(800, 120, 200, 25);
		panel.add(fecha);

		etiqueta = new JLabel("Plataformas disponibles: ");
		etiqueta.setBounds(600, 170, 250, 25);
		panel.add(etiqueta);
		plataformasDisponibles = new JComboBox<String>();
		plataformasDisponibles.setToolTipText("Seleccione o ingrese el tipo de plataforma.");
		plataformasDisponibles.setBounds(800, 170, 200, 25);
		panel.add(plataformasDisponibles);

		agregar = new JButton("Agregar");
		icono = new ImageIcon("src/videojuegos/imagenes/agregar.png");
		agregar.setIcon(icono);
		agregar.setToolTipText("Agregar un tipo de plataforma.");
		agregar.setBounds(780, 202, 120, 30);
		agregar.addActionListener(this);
		panel.add(agregar);

		quitar = new JButton("Quitar");
		icono = new ImageIcon("src/videojuegos/imagenes/quitar.png");
		quitar.setIcon(icono);
		quitar.setToolTipText("Quitar un tipo de plataforma.");
		quitar.setBounds(910, 202, 120, 30);
		quitar.addActionListener(this);
		panel.add(quitar);

		listaDeEstudios = new JList<String>(modelo);
		listaDeEstudios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane barra = new JScrollPane(listaDeEstudios);
		barra.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		barra.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listaDeEstudios.setToolTipText("Seleccione un tipo de plataforma.");
		barra.setBounds(800, 240, 200, 100);
		panel.add(barra);

		etiqueta = new JLabel("Imagen del videojuego: ");
		etiqueta.setBounds(600, 370, 250, 25);
		panel.add(etiqueta);

		botonSeleccionarImagen = new JButton("Seleccionar imagen");
		icono = new ImageIcon("src/videojuegos/imagenes/seleccionarImagen.png");
		botonSeleccionarImagen.setIcon(icono);
		botonSeleccionarImagen.setToolTipText("Seleccione la imagen del videojuego.");
		botonSeleccionarImagen.setBounds(800, 370, 200, 30);
		botonSeleccionarImagen.addActionListener(this);
		panel.add(botonSeleccionarImagen);

		etiquetaImage = new JLabel();
		etiquetaImage.setBounds(800, 390, 200, 180);
		panel.add(etiquetaImage);

		icono = new ImageIcon("src/videojuegos/imagenes/nuevo.png");
		botonNuevo = new JButton("Nuevo");
		botonNuevo.setToolTipText("Crea una nuevo videojuego.");
		botonNuevo.setBounds(150, 580, 120, 35);
		botonNuevo.addActionListener(this);
		botonNuevo.setIcon(icono);
		panel.add(botonNuevo);

		icono = new ImageIcon("src/videojuegos/imagenes/modificar.png");
		botonModificar = new JButton("Modificar");
		botonModificar.setToolTipText("Modifica el videojuego seleccionado.");
		botonModificar.setBounds(300, 580, 120, 35);
		botonModificar.addActionListener(this);
		botonModificar.setIcon(icono);
		panel.add(botonModificar);

		icono = new ImageIcon("src/videojuegos/imagenes/guardar.png");
		botonGuardar = new JButton("Guardar");
		botonGuardar.setToolTipText("Guarda el videojuego.");
		botonGuardar.setBounds(450, 580, 120, 35);
		botonGuardar.addActionListener(this);
		botonGuardar.setIcon(icono);
		panel.add(botonGuardar);

		icono = new ImageIcon("src/videojuegos/imagenes/eliminar.png");
		botonEliminar = new JButton("Eliminar");
		botonEliminar.setToolTipText("Elimina el videojuego seleccionado.");
		botonEliminar.setBounds(600, 580, 120, 35);
		botonEliminar.addActionListener(this);
		botonEliminar.setIcon(icono);
		panel.add(botonEliminar);

		icono = new ImageIcon("src/videojuegos/imagenes/cancelar.png");
		botonCancelar = new JButton("Cancelar");
		botonCancelar.setToolTipText("Cancela los datos ingresados.");
		botonCancelar.setBounds(750, 580, 120, 35);
		botonCancelar.addActionListener(this);
		botonCancelar.setIcon(icono);
		panel.add(botonCancelar);

		icono = new ImageIcon("src/videojuegos/imagenes/consulta.png");
		botonConsultar = new JButton("Consultar");
		botonConsultar.setToolTipText("Consulta los videojuegos guardadas.");
		botonConsultar.setBounds(900, 580, 120, 35);
		botonConsultar.addActionListener(this);
		botonConsultar.setIcon(icono);
		panel.add(botonConsultar);

		this.inicializar();
		this.establecerPoliticaFoco();
		this.add(panel);
		this.setTitle("Catálogo de videojuegos.");
		this.setSize(new Dimension(1200, 720));
		this.setIconImage(
				Toolkit.getDefaultToolkit().getImage(getClass().getResource("/videojuegos/imagenes/icono.png")));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		this.setVisible(true);
	}

	@Override
	/**
	 * Encargado del manejo de eventos producido al hacer clic sobre un componente
	 * de la ventana principal.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(salir)) {
			this.salir();
		} else if (arg0.getSource().equals(nuevo) || arg0.getSource().equals(botonNuevo)) {
			this.nuevo();
		} else if (arg0.getSource().equals(modificar) || arg0.getSource().equals(botonModificar)) {
			this.modificar();
		} else if (arg0.getSource().equals(guardar) || arg0.getSource().equals(botonGuardar)) {
			this.guardar();
		} else if (arg0.getSource().equals(eliminar) || arg0.getSource().equals(botonEliminar)) {
			this.eliminar(this.comboVideojuegos.getSelectedIndex());
		} else if (arg0.getSource().equals(cancelar) || arg0.getSource().equals(botonCancelar)) {
			this.cancelar();
		} else if (arg0.getSource().equals(agregar)) {
			this.agregarOpcion();
		} else if (arg0.getSource().equals(quitar)) {
			this.quitarOpcion(this.listaDeEstudios.getSelectedIndex());
		} else if (arg0.getSource().equals(botonSeleccionarImagen)) {
			this.seleccionarImagen();
		} else if (arg0.getSource().equals(comboVideojuegos)) {
			this.consultar(this.comboVideojuegos.getSelectedIndex());
		} else if (arg0.getSource().equals(consultar) || arg0.getSource().equals(botonConsultar)) {
			new DialogoConsulta();
		}
	}

	// Método privado incializar encargado de desactivar los campos y las opciones.
	private void inicializar() {
		// Establece imagen por defecto.
		File directorio = new File("img");
		if(!directorio.exists()) {
			directorio.mkdir();
		}
		archivoImagen = new File("img/default.png");
		// Método para deshabilitar todos los componentes.
		this.deshabilitarCampos();
		this.nuevo.setEnabled(true);
		this.botonNuevo.setEnabled(true);
		this.guardar.setEnabled(false);
		this.botonGuardar.setEnabled(false);
		this.cancelar.setEnabled(false);
		this.botonCancelar.setEnabled(false);
		this.verificarCombo();
		// Metodos que añaden los elemtos al combo y List de los archivos de texto.
		this.leerOpcionesEstudios(rutaArchivoDeTextoEstudios.getAbsolutePath());
		this.leerOpcionesPlataformas(rutaArchivoDeTextoPlataformas.getAbsolutePath());
	}

	// Método privado verificarCombo encargado de validar si el combo inicial tiene
	// elementos.
	private void verificarCombo() {
		if (comboVideojuegos.getItemCount() == 0) {
			this.modificar.setEnabled(false);
			this.botonModificar.setEnabled(false);
			this.eliminar.setEnabled(false);
			this.botonEliminar.setEnabled(false);
			this.comboVideojuegos.setEnabled(false);
		} else {
			this.modificar.setEnabled(true);
			this.botonModificar.setEnabled(true);
			this.eliminar.setEnabled(true);
			this.botonEliminar.setEnabled(true);
			this.comboVideojuegos.setEnabled(true);
			this.comboVideojuegos.setSelectedIndex(0);
		}
	}

	// Método privado combo encargado de obtener el objeto seleccionado y extraer
	// los valores de las propiedades y establecerlos en los campos.
	private void consultar(int selectedIndex) {
		if (comboVideojuegos.getSelectedIndex() >= 0) {
			baseDatosGames = coleccionVideojuegos.get(selectedIndex);
			this.id.setText(baseDatosGames.getId());
			this.nombre.setText(baseDatosGames.getNombre());
			this.precio.setText("" + baseDatosGames.getPrecio());
			this.peso.setValue(baseDatosGames.getPeso());
			this.idioma.setText(baseDatosGames.getIdioma());
			this.e.setSelected(baseDatosGames.isE());
			this.t.setSelected(baseDatosGames.isT());
			this.m.setSelected(baseDatosGames.isM());
			this.accion.setSelected(baseDatosGames.isAccion());
			this.aventura.setSelected(baseDatosGames.isAventura());
			this.shooter.setSelected(baseDatosGames.isShooter());
			this.rpg.setSelected(baseDatosGames.isRpg());
			this.estrategia.setSelected(baseDatosGames.isEstrategia());
			this.survival.setSelected(baseDatosGames.isSurvival());
			this.horror.setSelected(baseDatosGames.isAventura());
			this.mundoAbierto.setSelected(baseDatosGames.isMundoAbierto());
			this.estudiosDesarrollos.setSelectedItem(baseDatosGames.getEstudioDesarrollo());
			this.fecha.setDate(baseDatosGames.getFechaDeLanzamiento());
			modelo.clear();
			modelo.removeAllElements();
			if (baseDatosGames.getPlataformas() != null) {
				String[] arreglo = baseDatosGames.getPlataformas();
				for (String arr : arreglo) {
					modelo.addElement(arr);
				}
			}
			// Obtiene la imagen guardada en dominio.
			ImageIcon imagen = new ImageIcon(baseDatosGames.getImagen());
			Image imagenEscala = imagen.getImage().getScaledInstance(200, -1, Image.SCALE_SMOOTH);
			etiquetaImage.setIcon(new ImageIcon(imagenEscala));
			archivoImagen = new File(baseDatosGames.getImagen());
		}
	}

	// Método privado guardar encargado de validar si al guardar el videjuego es nuevo o una modificada para poder guardarla.
	private void guardar() {
		try {
			if (banderaNuevo) {
				baseDatosGames = new Videojuego();
				baseDatosGames.setId(this.id.getText());
				baseDatosGames.setNombre(this.nombre.getText());
				baseDatosGames.setPrecio("" + this.precio.getText());
				baseDatosGames.setPeso(Integer.parseInt("" + this.peso.getValue()));
				baseDatosGames.setIdioma(this.idioma.getText());
				baseDatosGames.setE(this.e.isSelected());
				baseDatosGames.setT(this.t.isSelected());
				baseDatosGames.setM(this.m.isSelected());
				baseDatosGames.setAccion(this.accion.isSelected());
				baseDatosGames.setAventura(this.aventura.isSelected());
				baseDatosGames.setShooter(this.shooter.isSelected());
				baseDatosGames.setRpg(this.rpg.isSelected());
				baseDatosGames.setEstrategia(this.estrategia.isSelected());
				baseDatosGames.setSurvival(this.survival.isSelected());
				baseDatosGames.setHorror(this.horror.isSelected());
				baseDatosGames.setMundoAbierto(this.mundoAbierto.isSelected());
				baseDatosGames.setEstudioDesarrollo(this.estudiosDesarrollos.getSelectedItem().toString());
				baseDatosGames.setFechaDeLanzamiento(fecha.getDate());
				// Arreglo para guardar los elementos del modelo
				String[] arreglo = new String[modelo.getSize()];
				for (int i = 0; i < modelo.getSize(); i++) {
					arreglo[i] = (String) modelo.getElementAt(i);
				}
				baseDatosGames.setPlataformas(arreglo);
				baseDatosGames.setImagen(archivoImagen.getAbsolutePath());
				// Para detectar si la imagen es nueva
				if (!archivoImagen.getName().equals("default.png")) {
					StringTokenizer tokens = new StringTokenizer(archivoImagen.getName(), ".");
					tokens.nextToken();
					String extension = tokens.nextToken();
					File destino = new File("img/" + baseDatosGames.getId() + "." + extension);
					try {
						InputStream in = new FileInputStream(archivoImagen.getAbsolutePath());
						OutputStream out = new FileOutputStream(destino);
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						in.close();
						out.close();
					} catch (IOException e) {
					}
					// Asigna la ruta de la imagen copiada.
					baseDatosGames.setImagen(destino.getAbsolutePath());
				} else {
					// Asigna la ruta por defecto
					baseDatosGames.setImagen(archivoImagen.getAbsolutePath());
				}
				try {
					BaseDatosVideojuegos base = new BaseDatosVideojuegos(baseDatosGames);
					if (base.buscarVideojuego() != false) {
						if (base.insertarVideojuego() != false) {
							coleccionVideojuegos.add(baseDatosGames);
							comboVideojuegos.addItem(baseDatosGames);
							JOptionPane.showMessageDialog(null, "Nuevo videojuego guardado con éxito.",
									"Videojuego nuevo.", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null,
									"No se pudo guardar el videojuego en la base de datos.\nFavor de intentar nuevamente.",
									"Error en la base de datos.", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"No se puede guardar un videjuego con el mismo id.\nFavor de cambiar el id del nuevo videojuego.",
								"Videojuego existente.", JOptionPane.ERROR_MESSAGE);
					}
				} catch (BaseDatosException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error en las operaciones de los métodos.",
							JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error en la sentencia sql.",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				baseDatosGames = coleccionVideojuegos.get(this.comboVideojuegos.getSelectedIndex());

				baseDatosGames.setId(this.id.getText());
				baseDatosGames.setNombre(this.nombre.getText());
				baseDatosGames.setPrecio(Double.parseDouble("" + this.precio.getText()));
				baseDatosGames.setPeso(Integer.parseInt("" + this.peso.getValue()));
				baseDatosGames.setIdioma(this.idioma.getText());
				baseDatosGames.setT(this.t.isSelected());
				baseDatosGames.setAccion(this.accion.isSelected());
				baseDatosGames.setAventura(this.aventura.isSelected());
				baseDatosGames.setShooter(this.shooter.isSelected());
				baseDatosGames.setRpg(this.rpg.isSelected());
				baseDatosGames.setEstrategia(this.estrategia.isSelected());
				baseDatosGames.setSurvival(this.survival.isSelected());
				baseDatosGames.setHorror(this.horror.isSelected());
				baseDatosGames.setMundoAbierto(this.mundoAbierto.isSelected());
				baseDatosGames.setMundoAbierto(this.mundoAbierto.isSelected());
				baseDatosGames.setEstudioDesarrollo(this.estudiosDesarrollos.getSelectedItem().toString());
				baseDatosGames.setFechaDeLanzamiento(fecha.getDate());
				// Arreglo para guardar los elementos del modelo
				String[] arreglo = new String[modelo.getSize()];
				for (int i = 0; i < modelo.getSize(); i++) {
					arreglo[i] = (String) modelo.getElementAt(i);
				}
				baseDatosGames.setPlataformas(arreglo);
				// Para detectar si la imagen es nueva
				if (!archivoImagen.getName().equals("default.png")) {
					// Para detectar si ya tenia una imagen diferente a la de defecto
					File imgGuardada = new File(baseDatosGames.getImagen());
					// System.out.println(imgGuardada.getName());
					if (!imgGuardada.getName().equals("default.png")) {
						// Elimina la imagen.
						imgGuardada.delete();
					}
					StringTokenizer tokens = new StringTokenizer(archivoImagen.getName(), ".");
					tokens.nextToken();
					String extension = tokens.nextToken();
					File destino = new File("img/" + baseDatosGames.getId() + "." + extension);
					try {
						InputStream in = new FileInputStream(archivoImagen.getAbsolutePath());
						OutputStream out = new FileOutputStream(destino);
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						in.close();
						out.close();
					} catch (IOException e) {
					}
					// Asigna la ruta de la imagen copiada.
					baseDatosGames.setImagen(destino.getAbsolutePath());
				}

				try {
					BaseDatosVideojuegos base = new BaseDatosVideojuegos(baseDatosGames);
					if (base.modificarVideojuego() != false) {
						JOptionPane.showMessageDialog(null, "Videojuego modificado con éxito.",
								"videojuego modificado.", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null,
								"No se pudo modificar el videojuego en la base de datos.\nFavor de intentar nuevamente.",
								"Error en la base de datos.", JOptionPane.ERROR_MESSAGE);
					}

				} catch (BaseDatosException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error en las operaciones de los métodos.",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			this.cancelar();
		} catch (VideojuegoExcepcion bme) {
			JOptionPane.showMessageDialog(null, bme.getMessage(), "Error al guardar.", JOptionPane.ERROR_MESSAGE);
		}

	}

	// Método privado nuevo encargado de activar todos los campos,limpiarlos, poner
	// valores por defecto y habilitar guardar y cancelar
	private void nuevo() {
		this.banderaNuevo = true;
		this.comboVideojuegos.setEnabled(false);
		// Establece imagen por defecto.
		archivoImagen = new File("img/default.png");
		// habilita campos.
		this.habilitarCampos();
		// limpia y pone valores por defecto
		this.limpiarCampos();
		// Imagen por defecto
		ImageIcon imagen = new ImageIcon(archivoImagen.getAbsolutePath());
		Image imagenEscala = imagen.getImage().getScaledInstance(200, -1, Image.SCALE_SMOOTH);
		etiquetaImage.setIcon(new ImageIcon(imagenEscala));

		// Habilita guardar y cancelar
		this.guardar.setEnabled(true);
		this.botonGuardar.setEnabled(true);
		this.cancelar.setEnabled(true);
		this.botonCancelar.setEnabled(true);
		// Desactiva los otros
		this.botonNuevo.setEnabled(false);
		this.nuevo.setEnabled(false);
		this.modificar.setEnabled(false);
		this.botonModificar.setEnabled(false);
		this.eliminar.setEnabled(false);
		this.botonEliminar.setEnabled(false);
		// Limpia el modelo.
		modelo.clear();
	}

	// Método privado modificar encargado de habilitar los campos y las opciones
	// guardar y salir.
	private void modificar() {

		this.comboVideojuegos.setEnabled(false);
		// Activa los campos.
		this.habilitarCampos();
		// Habilita guardar y cancelar.
		this.guardar.setEnabled(true);
		this.botonGuardar.setEnabled(true);
		this.cancelar.setEnabled(true);
		this.botonCancelar.setEnabled(true);
		// Deshabilita los otros.
		this.nuevo.setEnabled(false);
		this.botonNuevo.setEnabled(false);
		this.modificar.setEnabled(false);
		this.botonModificar.setEnabled(false);
		this.eliminar.setEnabled(false);
		this.botonEliminar.setEnabled(false);

		this.id.setEditable(false);

	}

	// Método privado cancelar encargado de desactivar los campos y limpiarlos,
	// deshabilita las opciones guardar y cancelar y regresa al método inicio.
	private void cancelar() {
		this.banderaNuevo = false;
		// Limpia el modelo.
		modelo.clear();
		// Establece imagen por defecto.
		archivoImagen = new File("img/default.png");
		// Desactiva los campos.
		this.deshabilitarCampos();
		// Limpia los campos.
		this.limpiarCampos();
		// Deshabilita guardar y cancelar
		this.nuevo.setEnabled(true);
		this.botonNuevo.setEnabled(true);
		this.guardar.setEnabled(false);
		this.botonGuardar.setEnabled(false);
		this.cancelar.setEnabled(false);
		this.botonCancelar.setEnabled(false);
		// Verificar combo;
		this.verificarCombo();

	}

	// Método privado eliminar encargado de mandar un mensaje de confirmación al
	// usuario para posteriormente validar si elimina o no el videojuego.
	private void eliminar(int elemento) {
		assert elemento >= 0;
		if (JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el videojuego seleccionado?",
				"Eliminar videojuego.", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			BaseDatosVideojuegos base = new BaseDatosVideojuegos(coleccionVideojuegos.get(elemento));
			try {
				if (base.eliminarVideojuego() != false) {
					base.eliminarPlataformas();
					if (!archivoImagen.getName().equals("default.png")) {
						File imgGuardada = new File(archivoImagen.getAbsolutePath());
						imgGuardada.delete();
					}
					comboVideojuegos.removeItemAt(elemento);
					coleccionVideojuegos.remove(elemento);
					this.deshabilitarCampos();
					this.limpiarCampos();
					this.verificarCombo();
					JOptionPane.showMessageDialog(null, "Videojuego eliminado con éxito.",
							"Videojuego eliminado.", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"El videojuego no pudo ser eliminado.\nFavor de contactar con servicio técnico.",
							"No se elimino el videojuego.", JOptionPane.ERROR_MESSAGE);
				}
			} catch (BaseDatosException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error en las operaciones de los métodos.",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// Método privado salir encargado de validar si esta creando un nuevo o
	// modificando un videojuego y preguntar al usuario si desea guardar
	// antes de salir, si no o si desea cancelar.
	private void salir() {
		if (!guardar.isEnabled()) {
			System.exit(0);
		} else {
			int seleccion = JOptionPane.showConfirmDialog(this, "¿Desea guardar antes de salir?", "Salir.",
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (seleccion) {
			case JOptionPane.YES_OPTION:
				this.guardar();
				System.exit(0);
				break;

			case JOptionPane.NO_OPTION:
				System.exit(0);
				break;

			case JOptionPane.CANCEL_OPTION:
				break;

			default:
				break;
			}
		}
	}

	// Método que consiste en leer de un archivo de texto
	// las diferentes estudios de desarrollo de los videojuegos.
	private void leerOpcionesEstudios(String ruta) {
		try (BufferedReader lectura = new BufferedReader(new FileReader(ruta));) {
			try {
				String linea = lectura.readLine();
				while (linea != null) {
					// Añade las elementos al combo de estudios.
					estudiosDesarrollos.addItem(linea);
					linea = lectura.readLine();
				}
			} finally {
				lectura.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Ocurrio un error al leer el archivo con las estudios.\nFavor de verificar que exista el archivo o que no tengan un error de formato.",
					"Error de lectura del archivo.", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Método privado leerOpcionesPlataformas que consiste en leer de un archivo de texto
	// las diferentes plataformas de los videojuegos.
	private void leerOpcionesPlataformas(String ruta) {
		try (BufferedReader lectura = new BufferedReader(new FileReader(ruta));) {
			try {
				String linea = lectura.readLine();
				while (linea != null) {
					// Añade los elementos al combo de plataformas.
					plataformasDisponibles.addItem(linea);
					// Añade los elementos al JList de plataformas.
					linea = lectura.readLine();
				}
			} finally {
				lectura.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Ocurrio un error al leer el archivo con las plataformas.\nFavor de verificar que exista el archivo o que no tengan un error de formato.",
					"Error de lectura del archivo.", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Método para obtener en un arraylist las opciones del archivo de texto.
	private ArrayList<String> obtenerPlataformasArchivo(String ruta) {
		ArrayList<String> plataformas = new ArrayList<String>();
		try (BufferedReader lectura = new BufferedReader(new FileReader(ruta));) {
			try {
				String linea = lectura.readLine();
				while (linea != null) {
					plataformas.add(linea);
					linea = lectura.readLine();
				}
			} finally {
				lectura.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Ocurrio un error al leer el archivo con las plataformas.\nFavor de verificar que exista el archivo o que no tengan un error de formato.",
					"Error de lectura del archivo.", JOptionPane.ERROR_MESSAGE);
		}
		return plataformas;
	}

	// Método privado agregar que consiste en.
	private void agregarOpcion() {
		FileWriter txt;
		// Bandera para validar si el valor ingresado es nuevo
		boolean bandera = false;
		// Recibe en un string el valor ingresado o seleccionado
		String valor = plataformasDisponibles.getSelectedItem().toString();
		// Transforma la primera letra en mayuscula y las demas en minúsculas.
		String resultado = valor.toUpperCase().charAt(0) + valor.substring(1, valor.length()).toLowerCase();
		// Quita los espacios
		resultado = resultado.trim();
		// Valida si existe el elemento en el modelo
		for (int i = 0; i < modelo.size(); i++) {
			if (resultado.equals(modelo.get(i))) {
				bandera = true;
				break;
			}
		}
		if (bandera == true) {
			JOptionPane.showMessageDialog(null,
					"La plataforma que desea agregar ya existe.\nFavor de ingresar otro tipo de plataforma.",
					"Error el nuevo elemento ya existe.", JOptionPane.ERROR_MESSAGE);
		} else {

			// Valida si el elemento ya existe en el archivo de texto
			boolean banderaNuevo = false;
			for (int i = 0; i < obtenerPlataformasArchivo(rutaArchivoDeTextoPlataformas.getAbsolutePath()).size(); i++) {
				if (resultado.equals(obtenerPlataformasArchivo(rutaArchivoDeTextoPlataformas.getAbsolutePath()).get(i))) {
					banderaNuevo = true;
				}
			}
			// Si el elemento es nuevo lo inserta al archivo de texto
			if (banderaNuevo == false) {
				try {
					// Escribe en el archivo
					txt = new FileWriter(rutaArchivoDeTextoPlataformas, true);
					txt.write("\n" + resultado);
					txt.close();
					// Añade al combo
					plataformasDisponibles.addItem(resultado);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"Ocurrio un error al agregar el nuevo elemento al archivo de las plataformas.\nFavor de verificar que exista el archivo o que no tengan un error de formato.",
							"Error de escritura del archivo.", JOptionPane.ERROR_MESSAGE);
				}
			}

			// Obtiene el tamaño del modelo
			int j = 0;
			for (int i = 0; i < modelo.getSize(); i++) {
				if (modelo.get(i) != null) {
					j++;
				}
			}
			// Obtiene los valores del modelo ignorando los campos nulos
			String[] elem = new String[j];
			for (int i = 0; i < modelo.getSize(); i++) {
				if (modelo.get(i) != null) {
					elem[i] = modelo.get(i);
				}
			}
			// Vacia el modelo (esto se hace para eliminar los campos nulos que existan)
			modelo.clear();
			modelo.removeAllElements();
			// Añade al modelo
			for (int i = 0; i < elem.length; i++) {
				modelo.addElement(elem[i]);
			}
			modelo.addElement(resultado);

		}
	}

	// Método privado quitar.
	private void quitarOpcion(int seleccion) {
		if (seleccion >= 0) {
			// Quita del modelo el elemento
			modelo.remove(seleccion);
			// Obtiene el tamaño del modelo
			int j = 0;
			for (int i = 0; i < modelo.getSize(); i++) {
				if (modelo.get(i) != null) {
					j++;
				}
			}
			// Obtiene los valores del modelo ignorando los campos nulos
			String[] elem = new String[j];
			for (int i = 0; i < modelo.getSize(); i++) {
				if (modelo.get(i) != null) {
					elem[i] = modelo.get(i);
				}
			}
			// Vacia el modelo (esto se hace para eliminar los campos nulos que existan)
			modelo.clear();
			modelo.removeAllElements();
			// Añade al modelo
			for (int i = 0; i < elem.length; i++) {
				modelo.addElement(elem[i]);
			}

			// tipoDePlataforma.removeItemAt(seleccion);
		} else {
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún elemento de la lista para quitar",
					"Quitar elemento.", JOptionPane.WARNING_MESSAGE);
		}
	}

	// Método privado seleccionarImagen que consiste en seleccionar una imagen jpg o
	// png del ordenador.
	private void seleccionarImagen() {
		JFileChooser dialogo = new JFileChooser();
		dialogo.setDialogTitle("Seleccionar imagen del videojuego");
		FileFilter filtro1 = new FileNameExtensionFilter("Imágenes png", "png", "PNG");
		FileFilter filtro2 = new FileNameExtensionFilter("Imágenes jpg", "jpg", "JPG");
		dialogo.setFileFilter(filtro1);
		dialogo.setFileFilter(filtro2);
		dialogo.setAcceptAllFileFilterUsed(false);
		dialogo.setMultiSelectionEnabled(false);
		int valor = dialogo.showOpenDialog(null);
		if (valor == JFileChooser.APPROVE_OPTION) {
			// Se guarda la ruta temporal de la imagen.
			archivoImagen = dialogo.getSelectedFile();
			if (archivoImagen.exists()) {
				// Se obtiene la ruta para mostrar la imagen en la pantalla
				ImageIcon imagen = new ImageIcon(archivoImagen.getAbsolutePath());
				Image imagenEscala = imagen.getImage().getScaledInstance(200, -1, Image.SCALE_SMOOTH);
				etiquetaImage.setIcon(new ImageIcon(imagenEscala));
			} else {
				JOptionPane.showMessageDialog(this,
						"No se pudo encontrar el archivo, favor de intentar con otra imagen.",
						"Error al seleccionar imagen.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// Método privado desactivarCampos encargado de desactivar todos los campos.
	private void deshabilitarCampos() {
		this.id.setEditable(false);
		this.nombre.setEditable(false);
		this.precio.setEditable(false);
		this.peso.setEnabled(false);
		this.idioma.setEditable(false);
		this.e.setEnabled(false);
		this.t.setEnabled(false);
		this.m.setEnabled(false);
		this.accion.setEnabled(false);
		this.aventura.setEnabled(false);
		this.shooter.setEnabled(false);
		this.rpg.setEnabled(false);
		this.estrategia.setEnabled(false);
		this.survival.setEnabled(false);
		this.horror.setEnabled(false);
		this.mundoAbierto.setEnabled(false);
		this.estudiosDesarrollos.setEnabled(false);
		this.fecha.setEnabled(false);
		this.plataformasDisponibles.setEnabled(false);
		this.agregar.setEnabled(false);
		this.quitar.setEnabled(false);
		this.listaDeEstudios.setEnabled(false);
		this.botonSeleccionarImagen.setEnabled(false);
		this.etiquetaImage.setEnabled(false);
	}

	// Método privado limpiarCampos encargado de limpiar todos los campos.
	private void limpiarCampos() {
		this.id.setText("");
		this.nombre.setText("");
		this.precio.setText("0.0");
		this.peso.setValue(0);
		this.idioma.setText("");
		this.e.setSelected(false);
		this.t.setSelected(false);
		this.m.setSelected(true);
		this.accion.setSelected(false);
		this.aventura.setSelected(false);
		this.shooter.setSelected(false);
		this.rpg.setSelected(false);
		this.estrategia.setSelected(false);
		this.survival.setSelected(false);
		this.horror.setSelected(false);
		this.mundoAbierto.setSelected(false);
		this.fecha.setDate(new Date());
		this.estudiosDesarrollos.setSelectedIndex(0);
		this.plataformasDisponibles.setSelectedIndex(0);
		this.listaDeEstudios.clearSelection();
		this.etiquetaImage.setIcon(null);
		this.modelo.clear();
		this.modelo.removeAllElements();
	}

	// Método privado habilitarCampos encargado de habilitar todos los campos.
	private void habilitarCampos() {
		this.id.setEditable(true);
		this.nombre.setEditable(true);
		this.precio.setEditable(true);
		this.peso.setEnabled(true);
		this.idioma.setEditable(true);
		this.e.setEnabled(true);
		this.t.setEnabled(true);
		this.m.setEnabled(true);
		this.accion.setEnabled(true);
		this.aventura.setEnabled(true);
		this.shooter.setEnabled(true);
		this.rpg.setEnabled(true);
		this.estrategia.setEnabled(true);
		this.survival.setEnabled(true);
		this.horror.setEnabled(true);
		this.mundoAbierto.setEnabled(true);
		this.estudiosDesarrollos.setEnabled(true);
		this.estudiosDesarrollos.setEditable(true);
		this.fecha.setEnabled(true);
		this.plataformasDisponibles.setEnabled(true);
		this.plataformasDisponibles.setEditable(true);
		this.agregar.setEnabled(true);
		this.quitar.setEnabled(true);
		this.listaDeEstudios.setEnabled(true);
		this.botonSeleccionarImagen.setEnabled(true);
		this.etiquetaImage.setEnabled(true);
	}

	// Método para establecer la politica de foco.
	private void establecerPoliticaFoco() {
		Vector<Component> componentes = new Vector<Component>();
		componentes.add(nombre);
		componentes.add(precio);
		componentes.add(idioma);
		componentes.add(peso);
		componentes.add(e);
		componentes.add(t);
		componentes.add(m);
		componentes.add(accion);
		componentes.add(aventura);
		componentes.add(shooter);
		componentes.add(rpg);
		componentes.add(estrategia);
		componentes.add(survival);
		componentes.add(horror);
		componentes.add(mundoAbierto);
		componentes.add(estudiosDesarrollos);
		componentes.add(fecha);
		componentes.add(plataformasDisponibles);
		componentes.add(agregar);
		componentes.add(quitar);
		componentes.add(listaDeEstudios);
		componentes.add(botonSeleccionarImagen);
		componentes.add(botonNuevo);
		componentes.add(botonModificar);
		componentes.add(botonGuardar);
		componentes.add(botonEliminar);
		componentes.add(botonCancelar);
		MiFocusTraversalPolicy politicaFoco = new MiFocusTraversalPolicy(componentes);
		this.setFocusTraversalPolicy(politicaFoco);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		this.salir();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
