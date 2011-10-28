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
	SerialPort puertoSerie;
	CommPortIdentifier candidatoPuertoSerie;
	List<CommPortIdentifier> puertosSerie;
	static Enumeration<CommPortIdentifier> listaPuertos = CommPortIdentifier.getPortIdentifiers();
	
	public PuertoSerie(String nombrePuerto) {
		puertosSerie = new LinkedList<CommPortIdentifier>();
		puertoSerie = null;
		candidatoPuertoSerie = null;
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
	 * Comprueba si el puerto serie buscado, esta disponible
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
	 * @return	true si ha sido posible
	 * 			false si no lo ha sido
	 */
	public boolean abrir() {
		try {
			puertoSerie = (SerialPort)candidatoPuertoSerie.open(candidatoPuertoSerie.getName(), 5000);
		} catch (PortInUseException e) {
			return false;
		}
		return true;

	}
	/**
	 * Comprueba si el puerto está disponible
	 * @return 
	 */
	public boolean estaDisponible() {
		return !candidatoPuertoSerie.isCurrentlyOwned();

	}
	/**
	 * Escribe una cadena en el flujo de salida 
	 * @param cadena
	 * @return	si se ha escrito
	 */
	public boolean escribir(String cadena) {
		if(puertoSerie==null) return false;
		
		try {
			puertoSerie.getOutputStream().write(cadena.getBytes());
		} catch (IOException e) {
			return false;
		}
		
		return true;

	}
	/**
	 * Lee una secuencia de bytes del flujo de entrada, y los convierte a una cadena 
	 * @return Un String con los datos leidos
	 * 		   null si se ha producido un error	
	 */
	public String leer() {
		byte [] bytes;
		if(puertoSerie==null) 
			return null;
			try {
				//Tamaño del array = numero de bytes disponibles
				bytes = new byte [puertoSerie.getInputStream().available()+1];
			} catch (IOException e1) {
				return null;
			}
		try {
			//Leemos
			puertoSerie.getInputStream().read(bytes);
		} catch (IOException e) {
			return null;
		}
		
		return new String(bytes);

	}
}
