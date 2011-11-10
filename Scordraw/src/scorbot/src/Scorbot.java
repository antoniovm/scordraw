package scorbot.src;

import java.util.LinkedList;


public class Scorbot {

	PuertoSerie ps;
	LinkedList<String> variablesPosicion;
	
	public Scorbot() {
		ps=new PuertoSerie("COM4");
		variablesPosicion = new LinkedList<String>();
		ps.abrir();
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
	 * Comprobar si la respuesta termina con un "Done."
	 * @param respuesta
	 * @return true si asi es, false en caso contrario
	 */
	private boolean comprobarEstadoDeRespuesta(String respuesta) {
		return respuesta.contains("Done.");

	}
	
	//------SIN TERMINAR, HAY QUE AÑADIR LOS METODOS DE ACLPARSER COMPROBANDO LA RESPUESTA DEL ROBOT
	//------PERO YA HE ACABAO UN POCO HASTA LAS COJONES DE JAVA POR HOY
	

}