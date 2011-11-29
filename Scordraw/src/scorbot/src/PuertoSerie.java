package scorbot.src;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;

public class PuertoSerie {
	private SerialPort puertoSerie;
	private CommPortIdentifier candidatoPuertoSerie;
	private List<CommPortIdentifier> puertosSerie;
	private String respuesta;
	static private Enumeration<CommPortIdentifier> listaPuertos = CommPortIdentifier.getPortIdentifiers();
	
	public PuertoSerie(String nombrePuerto) {
		puertosSerie = new LinkedList<CommPortIdentifier>();
		puertoSerie = null;
		candidatoPuertoSerie = null;
		respuesta="";
		buscarPuertosSerieDisponibles();
		buscarPuertoSerie(nombrePuerto);
	}
	/**
	 * Muestra por pantalla los puertos serie disponibles en el sistema
	 */
	public static void mostrarPuertosSerieDisponibles(){
		//Se inicializa de nuevo para poder volverlo a recorrer
		listaPuertos=CommPortIdentifier.getPortIdentifiers(); 
		while(listaPuertos.hasMoreElements()){
			CommPortIdentifier portId = listaPuertos.nextElement();
			if((portId.getPortType() == CommPortIdentifier.PORT_SERIAL)&&!portId.isCurrentlyOwned()){ //== 1
				System.out.println(portId.getName()+"-"+"Puerto Serie ("+portId.getPortType()+")");
				
			}
			
		}
	}
	/**
	 * Comprueba y almacena cuales son los puertos serie disponibles
	 */
	private void buscarPuertosSerieDisponibles(){
		CommPortIdentifier puertoActual;
		
		while(listaPuertos.hasMoreElements()){
			puertoActual = listaPuertos.nextElement();
			if(puertoActual.getPortType() == CommPortIdentifier.PORT_SERIAL)
				puertosSerie.add(puertoActual);
		}
	}
	/**
	 * Comprueba si el puerto serie buscado esta disponible
	 * @param nombrePuerto
	 */
	private void buscarPuertoSerie(String nombrePuerto) {
		CommPortIdentifier puertoActual;
		Iterator<CommPortIdentifier> it = puertosSerie.iterator();
		while(it.hasNext()){
			puertoActual = it.next();
			if(puertoActual.getName().equals(nombrePuerto))
				candidatoPuertoSerie = puertoActual;
		}
	}
	/**
	 * Abre la comunicacion con el puerto
	 * @return	true si ha sido posible, false en caso contrario
	 */
	public boolean abrir() {
		try {
			puertoSerie = (SerialPort)candidatoPuertoSerie.open(candidatoPuertoSerie.getName(), 5000);
		} catch (Exception e) {
			return false;
		}
		return true;

	}
	/**
	 * Comprueba si el puerto está disponible
	 * @return true si lo esta, false en caso contrario
	 */
	public boolean estaDisponible() {
		return puertoSerie!=null;

	}
	/**
	 * Escribe un caracter en el flujo de salida 
	 * @param c
	 * @return	true si se ha escrito correctamente, false en caso contrario
	 */
	public boolean escribirCaracter(char c) {
		if(!estaDisponible()) return false;
		
		try {
			puertoSerie.getOutputStream().write(c);
		} catch (IOException e) {
			return false;
		}
		
		return true;

	}
	/**
	 * Envia una cadena al flujo de salida
	 * @param comando
	 * @return true si se ha enviado correctamente, false en caso contrario
	 */
	public boolean escribirCadena(String comando) {
		if(!estaDisponible()) return false;
		String leido="";
		for (int i = 0; i < comando.length(); i++) {
			escribirCaracter(comando.charAt(i));
			//interf.prompt(recibido+"\n>");
			//System.out.print(leido=leer().trim());
			
			respuesta+=leido=leer();
			
		}
		//System.out.println("\n"+(leido=leer().trim()));
		try {
			while(puertoSerie.getInputStream().available()>0)	//Leer lo que queda
				respuesta+=leido=leer();
			
		} catch (IOException e) {
			System.err.println("Se ha producido un error al leer del flujo de entrada");
			e.printStackTrace();
		}
		
		return true;

	}
	
	public String getRespuesta() {
		String respuesta=this.respuesta;
		this.respuesta="";
		return respuesta;
	}
	/**
	 * Lee una secuencia de bytes del flujo de entrada, y los convierte a una cadena 
	 * @return Un String con los datos leidos
	 * 		   null si se ha producido un error	
	 */
	public String leer() {
		byte [] bytes;
		if(!estaDisponible()) return null;
			try {
				//Tamaño del array = numero de bytes disponibles
				bytes = new byte [puertoSerie.getInputStream().available()+1];
			} catch (Exception e1) {
				return null;
			}
		try {
			//Leemos
			puertoSerie.getInputStream().read(bytes);
		} catch (IOException e) {
			return null;
		}
		
		return new String(bytes).trim();

	}
	/**
	 * Vacia el flujo de entrada
	 */
	public void flush() {
		if(!estaDisponible()) return;
		int disponibles=0;
		try {
			disponibles = puertoSerie.getInputStream().available();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(disponibles<1) return;
		byte [] buffer = new byte [disponibles];
		while(disponibles>0){
			try {
				puertoSerie.getInputStream().read(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
