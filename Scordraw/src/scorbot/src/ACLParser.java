package scorbot.src;

public class ACLParser {
	
	private static char run = '\r';
	
	/**
	 * Abre o cierra pinza
	 * @param estado
	 * @return La instruccion en ACL
	 */
	public String pinza(String estado) {
		if(estado.equals("A"))
			return "OPEN "+run;
		if(estado.equals("C"))
			return "CLOSE "+run;
		return "";
	}
	/**
	 * Mover a una posicion dada (debe que estar previamente declarada)
	 * @param posicion
	 * @return La instruccion en ACL
	 */
	public static String moverLineal(String posicion){
		return "MOVEL "+posicion.toUpperCase()+run;
	}
	/**
	 * Almacena 5 coordenadas absolutas en la posicion dada (debe que estar previamente declarada)
	 * @param posicion
	 * @param x
	 * @param y
	 * @param z
	 * @param p
	 * @param r
	 * @return La instruccion en ACL
	 */
	public static String guardarPosicionAbsoluta(String posicion, int x, int y, int z, int p, int r) {
		return "TEACH "+posicion.toUpperCase()+run+  x  +run+  y  +run+  z  +run+  p  +run+  r  +run;

	}
	/**
	 * Almacena 5 coordenadas relativas a la posicion antigua pasada por parametro en la posicion nueva dada (ambas deben estar previamente declaradas)
	 * @param posicionNueva
	 * @param posicionAntigua
	 * @param x
	 * @param y
	 * @param z
	 * @param p
	 * @param r
	 * @return La instruccion en ACL
	 */
	public static String guardarPosicionRelativa(String posicionNueva,String posicionAntigua, int x, int y, int z, int p, int r) {
		return "TEACHR "+posicionNueva.toUpperCase()+" "+posicionAntigua.toUpperCase()+run+  x  +run+  y  +run+  z  +run+  p  +run+  r  +run;

	}
	/**
	 * Reserva espacio de tipo posicion
	 * @param nombre
	 * @return La instruccion en ACL
	 */
	public static String declararPosicion(String nombre) {
		return "DEFP "+nombre.toUpperCase()+run;
	}
	/**
	 * Establece la velocidad recibida por parametro (1-100)
	 * @param velocidad
	 * @return La instruccion en ACL
	 */
	public static String velocidad(int velocidad) {
		return "SPEED "+velocidad+run;

	}
	/**
	 * Realiza una para critica del robot
	 * @return La instruccion en ACL
	 */
	public static String abortar() {
		return "A "+run;
	}
	/**
	 * Suspende el robot durante el tiempo indicado por parametro
	 * @param tiempo (centesimas de segundo)
	 * @return La instruccion en ACL
	 */
	public static String retardar(int tiempo) {
		return "DELAY "+tiempo+run;
	}
	/**
	 * Reserva n posiciones consecutivas, siendo n el tamaño pasado por parametro
	 * @param nombre
	 * @param tamano
	 * @return La instruccion en ACL
	 */
	public static String declararVectorPosiciones(String nombre, int tamano) {
		return "DIMP "+nombre+"["+tamano+"]"+run;
	}
	/**
	 * Lleva al robot a su posicion inicial
	 * @return La instruccion en ACL
	 */
	public static String home() {
		return "HOME "+run;
	}
}
