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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

import videojuegos.basedatos.*;
import videojuegos.excepcion.BaseDatosException;

public class DialogoConsulta extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JCheckBox casillaNombre;
	private JTextField nombre;

	private JCheckBox casillaIdioma;
	private JTextField idioma;

	private JCheckBox casillaClasificacion;
	private JComboBox<String> clasificacion;

	private JCheckBox casillaEstudioDesarrollo;
	private JComboBox<String> estudio;

	private JCheckBox casillaPrecio;
	private JComboBox<String> precio;
	private JSpinner spinnerPrecio;

	private JButton botonConsultar;
	private JTable tabla;
	private BaseDatos bd;

	// Variable con la ruta del archivo de las estudio.
	private File rutaArchivoDeTextoEstudios = new File("estudios.txt");

	// Consulta predeterminada al cargar el dialogo consulta.
	private String CONSULTA_PREDETERMINADA = "SELECT nombre AS 'Nombre',"
			+ "precio AS 'Precio', idioma AS 'Idioma', peso AS 'Peso', " + "e AS 'E', " + "t AS 'T'," + "m AS 'M',"
			+ "accion AS 'Acción'," + "aventura AS 'Aventura'," + "shooter AS 'Shooter'," + "rpg AS 'RPG',"
			+ "estrategia AS 'Estrategia'," + "survival AS 'Survival'," + "horror AS 'Horror',"
			+ "mundo_abierto AS 'Mundo abierto'," + "estudio_desarrollo AS 'Estudio de desarrollo', "
			+ "fecha AS 'Fecha de lanzamiento'" + "FROM videojuego WHERE id = id";

	private ResultSetTableModel modelo;

	// Constructor donde se agregan los componentes visuales para el dialogo.
	public DialogoConsulta() {
		JPanel panelNorte = new JPanel();
		panelNorte.setLayout(new GridLayout(2, 6, 0, 0));
		JPanel panelTmp;
		panelTmp = new JPanel();
		casillaNombre = new JCheckBox("Nombre");
		panelTmp.add(casillaNombre);
		panelNorte.add(panelTmp);
		nombre = new JTextField();
		nombre.setPreferredSize(new Dimension(140, 25));
		nombre.setEditable(false);
		panelTmp.add(nombre);
		panelNorte.add(panelTmp);
		casillaNombre.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					nombre.setEditable(false);
					nombre.setText("");
				} else {
					nombre.setEditable(true);
				}
			}
		});
		panelTmp = new JPanel();
		casillaIdioma = new JCheckBox("Idioma");
		panelTmp.add(casillaIdioma);
		panelNorte.add(panelTmp);
		idioma = new JTextField();
		idioma.setPreferredSize(new Dimension(140, 25));
		idioma.setEditable(false);
		panelTmp.add(idioma);
		panelNorte.add(panelTmp);
		casillaIdioma.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					idioma.setEditable(false);
					idioma.setText("");
				} else {
					idioma.setEditable(true);
				}
			}
		});
		panelTmp = new JPanel();
		casillaClasificacion = new JCheckBox("Clasificacion");
		panelTmp.add(casillaClasificacion);
		panelNorte.add(panelTmp);
		clasificacion = new JComboBox<String>();
		clasificacion.setPreferredSize(new Dimension(150, 25));
		clasificacion.setEditable(false);
		clasificacion.setEnabled(false);
		clasificacion.addItem("e");
		clasificacion.addItem("t");
		clasificacion.addItem("m");
		panelTmp.add(clasificacion);
		panelNorte.add(panelTmp);
		casillaClasificacion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					clasificacion.setEnabled(false);
					clasificacion.setSelectedIndex(0);
				} else {
					clasificacion.setEnabled(true);
				}
			}
		});
		panelTmp = new JPanel();
		casillaEstudioDesarrollo = new JCheckBox("Estudios de desarrollo");
		panelTmp.add(casillaEstudioDesarrollo);
		panelNorte.add(panelTmp);
		estudio = new JComboBox<String>();
		estudio.setPreferredSize(new Dimension(150, 25));
		estudio.setEditable(false);
		estudio.setEnabled(false);
		Vector<String> auxConsulta;
		auxConsulta = consultarEstudios(rutaArchivoDeTextoEstudios.getAbsolutePath());
		for (String mat : auxConsulta) {
			estudio.addItem(mat);
		}
		panelTmp.add(estudio);
		panelNorte.add(panelTmp);
		casillaEstudioDesarrollo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					estudio.setEnabled(false);
					estudio.setSelectedIndex(0);
				} else {
					estudio.setEditable(true);
					estudio.setEnabled(true);
				}
			}
		});
		panelTmp = new JPanel();
		casillaPrecio = new JCheckBox("Precio");
		panelTmp.add(casillaPrecio);
		panelNorte.add(panelTmp);
		precio = new JComboBox<String>();
		precio.setEditable(false);
		precio.addItem("Mayor que");
		precio.addItem("Menor que");
		precio.addItem("Igual que");
		precio.setPreferredSize(new Dimension(100, 25));
		precio.setEnabled(false);
		panelTmp.add(precio);
		panelNorte.add(panelTmp);
		spinnerPrecio = new JSpinner();
		spinnerPrecio.setPreferredSize(new Dimension(80, 25));
		spinnerPrecio.setEnabled(false);
		panelTmp.add(spinnerPrecio);
		panelNorte.add(panelTmp);
		casillaPrecio.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					precio.setEnabled(false);
					spinnerPrecio.setEnabled(false);
					spinnerPrecio.setValue(0);
				} else {
					precio.setEnabled(true);
					spinnerPrecio.setEnabled(true);
				}
			}
		});
		panelTmp = new JPanel();
		botonConsultar = new JButton("Consultar");
		botonConsultar.addActionListener(this);
		panelTmp.add(botonConsultar);
		panelNorte.add(panelTmp);

		panelNorte.setPreferredSize(new Dimension(990, 200));

		this.add(panelNorte, BorderLayout.NORTH);

		/***************************************************************************************************/

		try {
			bd = new BaseDatos();
			modelo = new ResultSetTableModel(bd.getControlador(), bd.getUrl(), CONSULTA_PREDETERMINADA);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Faltan controladores", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Extracción de la Batos de Datos",
					JOptionPane.ERROR_MESSAGE);
		}

		/***************************************************************************************************/
		JPanel panelCentro = new JPanel();

		tabla = new JTable(modelo);
		JScrollPane panel = new JScrollPane(tabla);
		panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.setPreferredSize(new Dimension(900, 400));
		panelCentro.add(panel);
		this.add(panelCentro, BorderLayout.CENTER);

		/** Clase interna para cerrar ventana **/
		class MiEventoVentana extends WindowAdapter {
			public void windowClosing(WindowEvent arg0) {
				cerrar();
				// System.exit(0);
				setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}
		}

		this.addWindowListener(new MiEventoVentana());
		setTitle("Consulta de videojuegos.");
		this.setIconImage(
				Toolkit.getDefaultToolkit().getImage(getClass().getResource("/videojuegos/imagenes/consulta.png")));
		setSize(1000, 700);
		setResizable(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	/**
	 * Encargado del manejo de eventos producido al hacer clic sobre el boton
	 * consultar.
	 */
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource().equals(botonConsultar)) {
			consultar();
		}
	}

	// Método para consultar las estudio en los archivos.
	public Vector<String> consultarEstudios(String ruta) {
		Vector<String> marcas = new Vector<String>();
		try (BufferedReader lectura = new BufferedReader(new FileReader(ruta));) {
			try {
				String linea = lectura.readLine();
				while (linea != null) {
					marcas.add(linea);
					linea = lectura.readLine();
				}
			} finally {
				lectura.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Ocurrio un error al leer el archivo con los estudios.\nFavor de verificar que exista el archivo o que no tengan un error de formato.",
					"Error de lectura del archivo.", JOptionPane.ERROR_MESSAGE);
		}
		return marcas;
	}

	// Método para realizar la consulta predeterminada en la base de datos.
	public void consultar() {
		String consulta = CONSULTA_PREDETERMINADA;// FIXME
		if (casillaNombre.isSelected()) {
			consulta += " AND nombre LIKE '%" + nombre.getText() + "%'";
		}
		if (casillaIdioma.isSelected()) {
			consulta += " AND idioma LIKE '%" + idioma.getText() + "%'";
		}
		if (casillaClasificacion.isSelected()) {
			switch (clasificacion.getSelectedIndex()) {
			case 0:
				consulta += "AND e = 1";
				break;
			case 1:
				consulta += "AND t = 1";
				break;
			case 2:
				consulta += "AND m = 1";
				break;
			default:
				break;
			}
		}
		if (casillaEstudioDesarrollo.isSelected()) {
			consulta += " AND estudio_desarrollo LIKE '%" + (String) estudio.getSelectedItem() + "%'";
		}
		if (casillaPrecio.isSelected()) {
			consulta += " AND precio ";
			switch (precio.getSelectedIndex()) {
			case 0:
				consulta += ">";
				break;
			case 1:
				consulta += "<";
				break;
			case 2:
				consulta += "=";
				break;
			default:
				break;
			}
			consulta += spinnerPrecio.getValue();
		}
		try {
			modelo.establecerConsulta(consulta);
		} catch (IllegalStateException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, BaseDatosException.ERROR_EN_CONSULTA, "Extracción de datos",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cerrar() {
		modelo.desconectar();
	}
}
