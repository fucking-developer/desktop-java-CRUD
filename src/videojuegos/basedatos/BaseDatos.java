package videojuegos.basedatos;

/*
 * 
 * Jonathan Eduardo Ibarra Martínez
 * 
 * Versión: 08/01/2023
 * 
 * Reto 2.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import videojuegos.excepcion.*;

public class BaseDatos {
	private Connection conexion;
	private String controlador;
	private String url;

	// Constructor para establecer los valores del controlador y url.
	public BaseDatos() {
		this.controlador = "org.sqlite.JDBC";
		this.url = "jdbc:sqlite:" + "videojuegos.db";
	}

	// Método para conectarse a la base de datos.
	protected boolean realizarConexion() throws BaseDatosException {
		try {
			Class.forName(controlador);
			conexion = DriverManager.getConnection(url);
			return true;
		} catch (ClassNotFoundException e) {
			throw new BaseDatosException(BaseDatosException.NO_SE_ENCONTRO_DRIVER);
		} catch (SQLException e) {
			throw new BaseDatosException(BaseDatosException.BD_NO_ENCONTRADA);
		}
	}

	// Método para cerrar la conexión de la base de datos.
	protected void cerrarConexion() throws BaseDatosException {
		try {
			conexion.close();
		} catch (SQLException e) {
			throw new BaseDatosException(BaseDatosException.DESCONEXION);
		}
	}

	// Método para realizar una consulta a la base de datos.
	protected ResultSet realizarConsulta(String consulta) throws BaseDatosException {
		try {
			Statement instruccion;
			instruccion = conexion.createStatement();
			return instruccion.executeQuery(consulta);
		} catch (SQLException e) {
			throw new BaseDatosException(BaseDatosException.ERROR_EN_CONSULTA);
		}
	}

	// Método para realizar insert, update y delete en la base de datos.
	protected int realizarAccion(String accion) throws BaseDatosException {
		try {
			Statement instruccion;
			instruccion = conexion.createStatement();
			return instruccion.executeUpdate(accion);
		} catch (SQLException e) {
			throw new BaseDatosException(BaseDatosException.ERROR_EN_ACCION);
		}
	}

	// Método para consultar las tablas en la base de datos.
	protected Vector<String> consultarTablas() throws BaseDatosException {
		try {
			String[] tipoTabla = { "TABLE" };
			ResultSet nomTablas;
			nomTablas = conexion.getMetaData().getTables(null, null, null, tipoTabla);
			Vector<String> tablas = new Vector<String>();
			while (nomTablas.next()) {
				tablas.add(nomTablas.getString(3));
			}
			return tablas;
		} catch (SQLException e) {
			throw new BaseDatosException(BaseDatosException.PROBLEMA_BD);
		}
	}

	// Método get para obtener el controlador.
	public String getControlador() {
		return controlador;
	}

	// Método get para obtener el url.
	public String getUrl() {
		return url;
	}
}
