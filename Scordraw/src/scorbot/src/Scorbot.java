package scorbot.src;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;


public class Scorbot extends Thread{

	PuertoSerie ps;
	LinkedList<String> variablesPosicion;
	ColaCircularConcurrente<LinkedList<Point>> trazos;
	
	public Scorbot(ColaCircularConcurrente<LinkedList<Point>> trazos) {
		//PuertoSerie.mostrarPuertosSerieDisponibles();
		ps=new PuertoSerie("COM4");
		variablesPosicion = new LinkedList<String>();
		this.trazos=trazos;
		ps.abrir();
	}
	
	public boolean estaActivo() {
		return ps.estaDisponible();

	}
	
	/**
	 * Lleva al robot a su posicion inicial
	 * @return true si consigue volver a home, false en caso contrario
	 */
	public boolean home() {
		ps.escribirCadena(ACLParser.home());
		return comprobarEstadoDeRespuesta(ps.getRespuesta());
	}
	
	public boolean controlOn() {
		ps.escribirCadena(ACLParser.controlOn());
		return comprobarEstadoDeRespuesta(ps.getRespuesta());

	}
	
	/**
	 * Abre o cierra pinza
	 * @param estado
	 * @return true si abre o cierra la pinza, false en caso contrario o que se pase por parametro un estado no valido
	 */
	public boolean pinza(String estado) {
		String s = ACLParser.pinza(estado);
		if(s.equals("")) return false;
		ps.escribirCadena(s);
		return comprobarEstadoDeRespuesta(ps.getRespuesta());
	}
	
	/**
	 * Declara una posicion
	 * @param nombre
	 * @return true si lo ha conseguido, false en caso contrario
	 */
	public boolean declararPosicion(String nombre) {
		ps.escribirCadena(ACLParser.declararPosicion(nombre));
		return comprobarEstadoDeRespuesta(ps.getRespuesta());
	}
	
	/**
	 * Declara un vector de posiciones
	 * @param nombre
	 * @param tamano
	 * @return true si ha podido declarar el vector de posiciones, false en caso contrario
	 */
	public boolean declararVectorPosiciones(String nombre, int tamano) {
		ps.escribirCadena(ACLParser.declararVectorPosiciones(nombre, tamano));
		return comprobarEstadoDeRespuesta(ps.getRespuesta());
	}
	
	/**
	 * Comprobar si la respuesta termina con un "Done."
	 * @param respuesta
	 * @return true si asi es, false en caso contrario
	 */
	private boolean comprobarEstadoDeRespuesta(String respuesta) {
		System.out.println(respuesta.trim());
		return respuesta.contains("Done.");
	}
	
	/**
	 * Almacena 5 coordenadas absolutas en la posicion dada (debe que estar previamente declarada)
	 * @param posicion
	 * @param x
	 * @param y
	 * @param z
	 * @param p
	 * @param r
	 * @return true si ha podido guardar la posicion, false en caso contrario
	 */
	public boolean guardarPosicionAbsoluta(String posicion, int x, int y, int z, int p, int r) {
		ps.escribirCadena(ACLParser.guardarPosicionAbsoluta(posicion));
		System.out.println(ps.getRespuesta());
		ps.escribirCadena(ACLParser.numero(x));
		System.out.println(ps.getRespuesta());
		ps.escribirCadena(ACLParser.numero(y));
		System.out.println(ps.getRespuesta());
		ps.escribirCadena(ACLParser.numero(z));
		System.out.println(ps.getRespuesta());
		ps.escribirCadena(ACLParser.numero(p));
		System.out.println(ps.getRespuesta());
		ps.escribirCadena(ACLParser.numero(r));
		System.out.println(ps.getRespuesta());
		return comprobarEstadoDeRespuesta(ps.getRespuesta());
	}
	
	/**
	 * Guarda una posición relativa a otra posición anterior
	 * @param posicionNueva
	 * @param posicionAntigua
	 * @param x
	 * @param y
	 * @param z
	 * @param p
	 * @param r
	 * @return true si ha podido guardar la posicion, false en caso contrario
	 */
	public boolean guardarPosicionRelativa(String posicionNueva,String posicionAntigua, int x, int y, int z, int p, int r) {
		ps.escribirCadena(ACLParser.guardarPosicionRelativa(posicionNueva, posicionAntigua, x, y, z, p, r));
		return comprobarEstadoDeRespuesta(ps.getRespuesta());
	}
	
	/**
	 * Mover a una posicion dada (debe estar previamente declarada)
	 * @param posicion
	 * @return true si ha podido realizar el movimiento, false en caso contrario
	 */
	public boolean moverLineal(String posicion){
		ps.escribirCadena(ACLParser.moverLineal(posicion));
		return comprobarEstadoDeRespuesta(ps.getRespuesta());
	}
	public boolean mover(String posicion){
		ps.escribirCadena(ACLParser.mover(posicion));
		return comprobarEstadoDeRespuesta(ps.getRespuesta());
	}
	
	/**
	 * Establece la velocidad recibida por parametro (1-100)
	 * @param velocidad
	 * @return true si ha podido establecer la velocidad, false en caso contrario
	 */
	public boolean velocidad(int velocidad) {
		ps.escribirCadena(ACLParser.velocidad(velocidad));
		return comprobarEstadoDeRespuesta(ps.getRespuesta());

	}
	
	/**
	 * Realiza una parada critica del robot
	 * @return true si ha conseguido parar, false en caso contrario
	 */
	public boolean abortar() {
		ps.escribirCadena(ACLParser.abortar());
		return comprobarEstadoDeRespuesta(ps.getRespuesta());
	}
	
	/**
	 * Suspende el robot durante el tiempo indicado por parametro
	 * @param tiempo (centesimas de segundo)
	 * @return true si consigue suspender el robot, false en caso contrario
	 */
	public boolean retardar(int tiempo) {
		ps.escribirCadena(ACLParser.retardar(tiempo));
		return comprobarEstadoDeRespuesta(ps.getRespuesta());
	}
	
	public void describirTrazoTiempoIrreal() {
		LinkedList<Point> trazo = trazos.consumir();
		Iterator<Point> iterator = trazo.iterator();
		Point virtual=null, real=null;
		int i =3;
		
		declararPosicion(2+"");
		virtual = iterator.next();
		real=ConversorCoordenadas.convertir(virtual);
		guardarPosicionAbsoluta(2+"", real.x, real.y, 1000, -900, 0);
		controlOn();
		mover(2+"");
		
		
		for (; iterator.hasNext();i++) {
			declararPosicion(i+"");
			real=ConversorCoordenadas.convertir(virtual);
			guardarPosicionAbsoluta(i+"", real.x, real.y, 100, -900, 0);
			controlOn();
			mover(i+"");
			virtual = iterator.next();
			
		}
		declararPosicion(i+"");
		real=ConversorCoordenadas.convertir(virtual);
		guardarPosicionAbsoluta(i+"", real.x, real.y, 100, -900, 0);
		controlOn();
		mover(i+"");
		
		
		declararPosicion(i+"");
		guardarPosicionAbsoluta(i+"", real.x, real.y, 1000, -900, 0);
		controlOn();
		mover(i+"");
		

	}
	
	public void describirTrazoContinuo(){
		recorrer(declararGuardar());
		
		
	}
	
	private void recorrer(int i) {
		controlOn();
		for (int j = 2; j < i+1; j++) {
			mover(j+"");
		}
		
	}

	private int declararGuardar() {
		LinkedList<Point> trazo = trazos.consumir();
		Iterator<Point> iterator = trazo.iterator();
		Point virtual=null, real=null;
		int i =3;
		
		declararPosicion(2+"");
		virtual = iterator.next();
		real=ConversorCoordenadas.convertir(virtual);
		guardarPosicionAbsoluta(2+"", real.x, real.y, 1000, -900, 0);		
		
		for (; iterator.hasNext();i++) {
			declararPosicion(i+"");
			real=ConversorCoordenadas.convertir(virtual);
			guardarPosicionAbsoluta(i+"", real.x, real.y, 100, -900, 0);
			virtual = iterator.next();
			
		}
		declararPosicion(i+"");
		real=ConversorCoordenadas.convertir(virtual);
		guardarPosicionAbsoluta(i+"", real.x, real.y, 100, -900, 0);
		
		
		declararPosicion((i++)+"");
		guardarPosicionAbsoluta(i+"", real.x, real.y, 1000, -900, 0);
		
		return i;
	
	}
	
	@Override
	public void run() {
		while (true) {
			//describirTrazoTiempoIrreal();
			describirTrazoContinuo();
		}
	}

	public void vaciarSalida() {
		ps.flush();
		
	}
	
	

}