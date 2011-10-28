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
		buscarPuertosSerieDisponibles(nombrePuerto);
	}
	public static void mostrarPuertosSerieDisponibles(){
		listaPuertos=CommPortIdentifier.getPortIdentifiers();
		while(listaPuertos.hasMoreElements()){
			CommPortIdentifier portId = listaPuertos.nextElement();
			if((portId.getPortType() == CommPortIdentifier.PORT_SERIAL)&&!portId.isCurrentlyOwned()){ //== 1
				System.out.println(portId.getName()+"-"+"Puerto Serie ("+portId.getPortType()+")");
				
			}
			
		}
	}
	
	private void buscarPuertosSerieDisponibles(String nombrePuerto) {
		CommPortIdentifier puertoActual;
		
		while(listaPuertos.hasMoreElements()){
			puertoActual = listaPuertos.nextElement();
			if(puertoActual.getName().equals(nombrePuerto))
				candidatoPuertoSerie = puertoActual;
			
				
		}

	}
	
	public boolean abrir() {
		try {
			puertoSerie = (SerialPort)candidatoPuertoSerie.open(candidatoPuertoSerie.getName(), 5000);
		} catch (PortInUseException e) {
			return false;
		}
		return true;

	}
	public boolean estaDisponible() {
		return !candidatoPuertoSerie.isCurrentlyOwned();

	}
	public boolean escribir(String cadena) {
		if(puertoSerie==null) return false;
		
		try {
			puertoSerie.getOutputStream().write(cadena.getBytes());
		} catch (IOException e) {
			return false;
		}
		
		return true;

	}
	
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
			return "";
		}
		
		return new String(bytes).trim();

	}
}
