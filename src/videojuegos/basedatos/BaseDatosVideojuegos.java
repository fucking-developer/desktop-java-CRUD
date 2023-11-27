package videojuegos.basedatos;

/*
 * 
 * Jonathan Eduardo Ibarra Martínez.
 * 
 * Versión: 08/01/2023
 * 
 * Reto 2.
 * 
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import videojuegos.dominio.Videojuego;
import videojuegos.excepcion.BaseDatosException;

public class BaseDatosVideojuegos extends Videojuego {

	// Constructor que recibe la entidad, similar a constructor copia y asigna los valores del objeto.
	public BaseDatosVideojuegos(Videojuego bdm) {
		this.setId(bdm.getId());
		this.setPeso(bdm.getPeso());
		this.setNombre(bdm.getNombre());
		this.setIdioma(bdm.getIdioma());
		this.setFechaDeLanzamiento(bdm.getFechaDeLanzamiento());
		this.setImagen(bdm.getImagen());
		this.setE(bdm.isE());
		this.setT(bdm.isT());
		this.setM(bdm.isM());
		this.setAccion(bdm.isAccion());
		this.setAventura(bdm.isAventura());
		this.setShooter(bdm.isShooter());
		this.setRpg(bdm.isRpg());
		this.setEstrategia(bdm.isEstrategia());
		this.setSurvival(bdm.isSurvival());
		this.setHorror(bdm.isHorror());
		this.setMundoAbierto(bdm.isMundoAbierto());
		this.setPlataformas(bdm.getPlataformas());
		this.setEstudioDesarrollo(bdm.getEstudioDesarrollo());
		this.setPrecio(bdm.getPrecio());
	}

	// Método que valida si la base de datos es del sistema.
	public static boolean validarBaseDatos() throws BaseDatosException {
		BaseDatos bd = new BaseDatos();
		bd.realizarConexion();
		Vector<String> tablas = new Vector<String>();
		tablas = bd.consultarTablas();
		bd.cerrarConexion();
		if (tablas.size() > 0) {
			for (String auxTablas : tablas) {
				if (auxTablas.compareTo("videojuego") == 0) {
					return true;
				}
			}
			throw new BaseDatosException(BaseDatosException.NO_ES_LA_BASE);
		} else {
			return false;
		}
	}

	// Método que consiste en crear las tablas correspondientes en la base de datos.
	public static void crearBaseDatos() throws BaseDatosException {
		try {
			BaseDatos bd = new BaseDatos();
			bd.realizarConexion();
			bd.realizarAccion("CREATE TABLE videojuego(id VARCHAR(20) PRIMARY KEY NOT NULL, nombre VARCHAR(30),"
					+ "precio REAL, idioma VARCHAR(30), peso INTEGER, e VARCHAR(30),t VARCHAR(30),m VARCHAR(30),"
					+ "accion VARCHAR(30),aventura VARCHAR(30),shooter VARCHAR(30),rpg VARCHAR(30),estrategia VARCHAR(30),"
					+ "survival VARCHAR(30),horror VARCHAR(30),mundo_abierto VARCHAR(30), estudio_desarrollo VARCHAR(40), "
					+ "fecha TEXT, imagen VARCHAR(500))");
			bd.realizarAccion("CREATE TABLE plataformas(id_plataformas VARCHAR(20), plataformas_disponibles VARCHAR(40))");
			bd.cerrarConexion();
		} catch (BaseDatosException e) {
			throw new BaseDatosException(BaseDatosException.NO_SE_PUDO_CREAR_LA_TABLA);
		}
	}


	public boolean insertarVideojuego() throws BaseDatosException {
		boolean bandera = false;
		realizarConexion();
		int valor = this.realizarAccion("INSERT INTO videojuego VALUES ('" + getId() + "','"
				+ getNombre() + "'," + getPrecio() + ",'"
				+ getIdioma() + "'," + getPeso() + "," + isE() + ","
				+ isT() + "," + isM() + "," + isAccion() + "," + isAventura() + ","
				+ isShooter() + "," + isRpg() + "," + isEstrategia()
				+ "," + isSurvival() + "," + isHorror() + ","
				+ isMundoAbierto() + ",'" + getEstudioDesarrollo() + "','" + getFechaDeLanzamiento()
				+ "','" + getImagen() + "')");
		if (valor != 0 ) {
			insertarPlataformas();
			bandera = true;
		}
		cerrarConexion();
		return bandera;
	}
	
	public boolean modificarVideojuego() throws BaseDatosException {
		boolean bandera = false;
		realizarConexion();
		int valor = this.realizarAccion("UPDATE videojuego SET nombre = '"+ getNombre()+"', precio = "+getPrecio()+", idioma='"+getIdioma()+"', peso ="+getPeso()+", e="+isE()+", t = "+isT()+", m = "+isM()+", accion = "+isAccion()+", aventura="+isAventura()+", shooter="+isShooter()+", rpg ="+isRpg()+", estrategia ="+isEstrategia()+", survival = "+isSurvival()+", horror = "+isHorror()+", mundo_abierto = "+isMundoAbierto()+", estudio_desarrollo ='"+getEstudioDesarrollo()+"', fecha = '"+getFechaDeLanzamiento()+"', imagen ='"+getImagen()+"' WHERE id = '"+getId()+"'");
		if (valor != 0) {
			eliminarPlataformas();
			insertarPlataformas();
			bandera = true;
		}
		cerrarConexion();
		return bandera;
	}
	

	public boolean eliminarVideojuego() throws BaseDatosException {
		boolean bandera = false;
		realizarConexion();
		int valor = this.realizarAccion("DELETE FROM videojuego WHERE id = '" + getId() +"'");
		if (valor != 0) {
			eliminarPlataformas();
			bandera = true;
		}
		cerrarConexion();
		return bandera;
	}
	

	public boolean buscarVideojuego() throws BaseDatosException, SQLException {
		boolean bandera = false;
		realizarConexion();
		ResultSet resultado = realizarConsulta("SELECT * FROM videojuego WHERE id = '"+getId()+"'");
		if(!resultado.next()) {
			bandera = true;
		}
		cerrarConexion();
		return bandera;
	}
	
	@SuppressWarnings("deprecation")
	public static ArrayList<Videojuego> obtenerVideojuegos() throws ClassNotFoundException, SQLException, BaseDatosException {
		BaseDatos bd = new BaseDatos();
		bd.realizarConexion();
		ResultSet resultado = bd.realizarConsulta("SELECT id, nombre, precio, idioma, peso, e, t, m,"
				+ "accion, aventura, shooter, rpg, estrategia, survival, horror, mundo_abierto, estudio_desarrollo, fecha, imagen FROM videojuego");
		ArrayList<Videojuego> lista = new ArrayList<Videojuego>();
		while (resultado.next()) {
			Videojuego base = new Videojuego();
			base.setId(resultado.getString(1));
			base.setNombre(resultado.getString(2));
			base.setPrecio(resultado.getDouble(3));
			base.setIdioma(resultado.getString(4));
			base.setPeso(resultado.getInt(5));
			base.setE(resultado.getBoolean(6));
			base.setT(resultado.getBoolean(7));
			base.setM(resultado.getBoolean(8));
			base.setAccion(resultado.getBoolean(9));
			base.setAventura(resultado.getBoolean(10));
			base.setShooter(resultado.getBoolean(11));
			base.setRpg(resultado.getBoolean(12));
			base.setEstrategia(resultado.getBoolean(13));
			base.setSurvival(resultado.getBoolean(14));
			base.setHorror(resultado.getBoolean(15));
			base.setMundoAbierto(resultado.getBoolean(16));
			base.setEstudioDesarrollo(resultado.getString(17));
			base.setFechaDeLanzamiento(new Date(resultado.getString(18)));
			base.setImagen(resultado.getString(19));
			base.setPlataformas(obtenerPlataformas(base));
			lista.add(base);
		}
		resultado.close();
		bd.cerrarConexion();
		return lista;
	}
	
	public static String[] obtenerPlataformas(Videojuego b) throws ClassNotFoundException, SQLException, BaseDatosException {
		BaseDatos bd = new BaseDatos();
		bd.realizarConexion();
		ResultSet resultado = bd.realizarConsulta("SELECT plataformas_disponibles FROM plataformas WHERE id_plataformas = '"+b.getId()+"'");
		String[] lista = new String[5];
		int i=0;
		while (resultado.next()) {
			lista[i] = resultado.getString(1);
			i++;
		}
		resultado.close();
		bd.cerrarConexion();
		return lista;
	}
	


	public void insertarPlataformas() throws BaseDatosException {
		realizarConexion();
		for(int i=0; i<getPlataformas().length; i++) {
			realizarAccion("INSERT INTO plataformas VALUES ('" + getId() + "','"+getPlataformas()[i]+"')");
		}
		cerrarConexion();
	}
	

	public void eliminarPlataformas() throws BaseDatosException{
		realizarConexion();
		realizarAccion("DELETE FROM plataformas WHERE id_plataformas = '" + getId() +"'");
		cerrarConexion();
	}

}
