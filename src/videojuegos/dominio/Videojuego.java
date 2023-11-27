package videojuegos.dominio;



/*
 * 
 * Jonathan Eduardo Ibarra Martínez
 * 
 * Versión: 30/10/2022
 * 
 * Práctica 4. Clase: Videojuego.
 * 
 */

import java.util.Date;

import videojuegos.basedatos.BaseDatos;
import videojuegos.excepcion.VideojuegoExcepcion;

public class Videojuego extends BaseDatos{

	
	// Propiedades de la entidad
	
	private String id;
	private int peso;
	private String nombre;
	private String idioma;
	private Date fechaLanzamiento;
	private String imagen;
	private boolean e;
	private boolean t;
	private boolean m;
	private boolean accion;
	private boolean aventura;
	private boolean shooter;
	private boolean rpg;
	private boolean estrategia;
	private boolean survival;
	private boolean horror;
	private boolean mundoAbierto;
	private String[] plataformas;
	private String estudioDesarrollo;
	private double precio;

	// Constructor sin parámetros, solo datos por defecto.
	public Videojuego() {
		this.setId("id");
		this.setPeso(0);
		this.setNombre("nombre");
		this.setIdioma("idioma");
		this.setFechaDeLanzamiento(new Date());
		this.setImagen("");
		this.setE(false);
		this.setT(false);
		this.setM(false);
		this.setAccion(false);
		this.setAventura(false);
		this.setShooter(false);
		this.setRpg(false);
		this.setEstrategia(false);
		this.setSurvival(false);
		this.setHorror(false);
		this.setMundoAbierto(false);
		this.setPlataformas(null);
		this.setEstudioDesarrollo("");
		this.setPrecio(1.0);
	}

	// Gets y sets por defecto de las variables.
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id.isEmpty()) {
			throw new VideojuegoExcepcion(VideojuegoExcepcion.ID_OBLIGATORIO);
		} else {
			this.id = id;
		}
	}
	
	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		if (peso >= 0 && peso <= 999) {
			this.peso = peso;
		} else {
			throw new VideojuegoExcepcion(VideojuegoExcepcion.PESO_RANGO);
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) throws VideojuegoExcepcion {
		if (nombre.isEmpty()) {
			throw new VideojuegoExcepcion(VideojuegoExcepcion.NOMBRE_OBLIGATORIO);
		} else {
			this.nombre = nombre;
		}

	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) throws VideojuegoExcepcion {
		if (idioma.isEmpty()) {
			throw new VideojuegoExcepcion(VideojuegoExcepcion.IDIOMA_OBLIGATORIO);
		} else {
			this.idioma = idioma;
		}
	}

	public Date getFechaDeLanzamiento() {
		return fechaLanzamiento;
	}

	public void setFechaDeLanzamiento(Date fechaDeLanzamiento) {
		this.fechaLanzamiento = fechaDeLanzamiento;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public boolean isE() {
		return e;
	}

	public void setE(boolean e) {
		this.e = e;
	}

	public boolean isT() {
		return t;
	}

	public void setT(boolean t) {
		this.t = t;
	}

	public boolean isM() {
		return m;
	}

	public void setM(boolean m) {
		this.m = m;
	}

	public boolean isAccion() {
		return accion;
	}

	public void setAccion(boolean accion) {
		this.accion = accion;
	}

	public boolean isAventura() {
		return aventura;
	}

	public void setAventura(boolean aventura) {
		this.aventura = aventura;
	}

	public boolean isShooter() {
		return shooter;
	}

	public void setShooter(boolean shooter) {
		this.shooter = shooter;
	}

	public boolean isRpg() {
		return rpg;
	}

	public void setRpg(boolean rpg) {
		this.rpg = rpg;
	}

	public boolean isEstrategia() {
		return estrategia;
	}

	public void setEstrategia(boolean estrategia) {
		this.estrategia = estrategia;
	}

	public boolean isSurvival() {
		return survival;
	}

	public void setSurvival(boolean survival) {
		this.survival = survival;
	}

	public boolean isHorror() {
		return horror;
	}

	public void setHorror(boolean horror) {
		this.horror = horror;
	}

	public boolean isMundoAbierto() {
		return mundoAbierto;
	}

	public void setMundoAbierto(boolean mundoAbierto) {
		this.mundoAbierto = mundoAbierto;
	}

	public String[] getPlataformas() {
		return plataformas;
	}

	public void setPlataformas(String[] plataformas) {
		this.plataformas = plataformas;
	}

	public String getEstudioDesarrollo() {
		return estudioDesarrollo;
	}

	public void setEstudioDesarrollo(String estudioDesarrollo) {
		this.estudioDesarrollo = estudioDesarrollo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		try {
			setPrecio(Double.parseDouble(precio));
		} catch (IllegalArgumentException e) {
			throw new VideojuegoExcepcion(VideojuegoExcepcion.PRECIO);
		}
	}

	public void setPrecio(double precioDelProducto) {
		if (precioDelProducto >= 0.0 && precioDelProducto <= 5000.00) {
			this.precio = precioDelProducto;
		} else {
			throw new VideojuegoExcepcion(VideojuegoExcepcion.PRECIO_RANGO);
		}
	}

	@Override
	public String toString() {
		return this.getNombre() + ", " + this.getEstudioDesarrollo() + ", "
				+ this.getFechaDeLanzamiento();
	}

}
