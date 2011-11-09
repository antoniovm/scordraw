package scorbot.src;


import javax.comm.*;

public class Scorbot {

public static void main(String[] args) throws UnsupportedCommOperationException, PortInUseException {
	System.out.print("Scorbot");
	PuertoSerie ps = new PuertoSerie("COM6");
	PuertoSerie.mostrarPuertosSerieDisponibles();
	
	
	if(ps.abrir())
		System.out.print("Comunicacion establecida");
	else{
		System.out.print("No se ha podido establecer comunicacion");
		System.exit(-1);
	}
	
	while(true){
		System.out.print("\n>");
		String lectura;
	
		lectura=ps.leer();
		ps.escribirCaracter(lectura.charAt(0));
		System.out.print(lectura);
	}
}

}
