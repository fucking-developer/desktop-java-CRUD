package videojuegos.excepcion;


public class VideojuegoExcepcion extends RuntimeException {
	private static final long serialVersionUID = 1L;

	// Constantes de datos obligatorios
	public static final String ID_OBLIGATORIO = "El id del videojuego es obligatorio.\nFavor de ingresar un id para el videojuego.";
	public static final String NOMBRE_OBLIGATORIO = "El nombre del videojuego es obligatorio.\nFavor de ingresar el nombre del videojuego.";
	public static final String IDIOMA_OBLIGATORIO = "El idioma del videojuego es obligatorio.\nFavor de ingresar el idioma del videojuego.";;
	// Constantes de rangos posibles
	public static final String PRECIO_RANGO = "El valor ingresado del precio es incorrecto.\nEl precio debe ser entre: [$0 - $1,000.0]";
	public static final String PESO_RANGO = "El valor ingresado del peso es incorrecto.\nEl peso debe ser entre: [0Gb - 999Gb]";
	// Constantes de formatos invalidos
	public static final String PRECIO = "El valor ingresado del precio es invalido.\nFavor de ingresar un valor valido";
	
	// Constructor el cual regresa el mensaje establecido en los setters de dominio.
	public VideojuegoExcepcion(String mensaje) {
		super(mensaje);
	}
}
