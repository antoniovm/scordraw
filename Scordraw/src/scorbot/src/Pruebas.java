package scorbot.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.io.BufferedReader;

import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.JProgressBar;
public class Pruebas {

public static void main(String[] args) throws IOException, UnsupportedCommOperationException, PortInUseException {
	ColaCircularConcurrente<LinkedList<Point>> trazos;
	trazos = new ColaCircularConcurrente<LinkedList<Point>>(3);
	JProgressBar progreso = new JProgressBar(0,100){
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setFont(new Font(g.getFont().getFontName(), 0, 20));
			FontMetrics fm =g.getFontMetrics();
			int i=fm.stringWidth(this.getValue()/this.getMaximum()*100+"%");
			g.setColor(Color.black);
			g.drawString(this.getValue()+"%", getWidth()/2-i/2, getHeight()/2+7);
			
		}
	};
	progreso.setPreferredSize(new Dimension(progreso.getWidth(), 30));
	Scorbot scb = new Scorbot(trazos, progreso);
	
	
	/*if(!scb.estaActivo()){
		System.out.println("FALLO!");
		System.exit(-1);
	}*/
	scb.vaciarSalida();
	
	//System.out.println("Home: "+scb.home());
	System.out.println("Control On: "+scb.controlOn());
	
	
	//trazos.encolar(new LinkedList<Point>());
	//PuertoSerie ps = new PuertoSerie("COM4");
	//PuertoSerie.mostrarPuertosSerieDisponibles();

	String comando,recibido;
	/*if(ps.abrir())
		System.out.print("Comunicacion establecida");
	else{
		System.out.print("No se ha podido establecer comunicacion");
		//System.exit(-1);
	}
	ps.flush();*/
	Interfaz interf = new Interfaz(trazos, scb);
	//ps.escribirCadena(ACLParser.mover(100, 10, 50, -900, 0));
	
	int n=2,x,y,z,p,r;
	
	String posicion="P"+2;
	
	scb.start();
	/*while(true){
		System.out.print("\n>");
		comando=(new BufferedReader(new InputStreamReader(System.in))).readLine().toUpperCase()+'\r'+'\n';
		scb.ps.escribirCadena(comando);
		
		//comando=leer().toUpperCase()+'\r';
		System.out.println("Velocidad: "+scb.velocidad(20));
		
		System.out.println("Declarar: "+scb.declararPosicion(posicion));
		System.out.print("X: ");
		x=Integer.parseInt(leer());
		System.out.print("Y: ");
		y=Integer.parseInt(leer());
		System.out.print("Z: ");
		z=Integer.parseInt(leer());
		System.out.print("P: ");
		p=Integer.parseInt(leer());
		System.out.print("R: ");
		r=Integer.parseInt(leer());
		
		//scb.ps.escribirCadena("COFF"+'\r');
		//scb.ps.escribirCadena("TEACH "+posicion+'\r');
		//System.out.println(scb.ps.getRespuesta());
		System.out.println("Teach: "+scb.guardarPosicionAbsoluta(posicion, x,y,z,p,r));
		
		//System.out.println("Control On: "+scb.controlOn());
		
		System.out.println("Mover: "+scb.mover(posicion));
		
	}*/



}
	public static String leer() {
		try {
			return (new BufferedReader(new InputStreamReader(System.in))).readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
	}
}

/*
 * INFORMACIÓN VARIA
 * 
 * COMMPORTIDENTIFIER
 * Communications port management. CommPortIdentifier is the central
 * class for controlling access to communications ports. It includes methods for: 
 * Determining the communications ports made available by the driver. 
 * Opening communications ports for I/O operations. 
 * Determining port ownership. 
 * Resolving port ownership contention. 
 * Managing events that indicate changes in port ownership status. 

 * An application first uses methods in CommPortIdentifier to negotiate
 * with the driver to discover which communication ports are available and then select a port for opening. It then uses methods in other classes like CommPort, ParallelPort and SerialPort to communicate through the port.
 * 
 * 
 * getPortIdentifiers
 * public static java.util.Enumeration getPortIdentifiers()
 * Obtains an enumeration object that contains a CommPortIdentifier object for each port in the system. 
 * 
 * 
 * 
 * COMMPORT
 * extends java.lang.Object

 * A communications port. CommPort is an abstract class that describes a
 * communications port made available by the underlying system. It includes
 * high-level methods for controlling I/O that are common to different kinds
 * of communications ports. SerialPort and ParallelPort are subclasses of
 * CommPort that include additional methods for low-level control of
 * physical communications ports. 

 * There are no public constructors for CommPort. Instead an application
 * should use the static method CommPortIdentifier.getPortIdentifiers
 * to generate a list of available ports. It then chooses a port from
 * this list and calls CommPortIdentifier.open to create a CommPort object.
 * Finally, it casts the CommPort object to a physical communications
 * device class like SerialPort or ParallelPort. 

 * After a communications port has been identified and opened it can be
 * configured with the methods in the low-level classes like SerialPort
 * and ParallelPort. Then an IO stream can be opend for reading and
 * writing data. Once the application is done with the port, it must call
 * the close method. Thereafter the application must not call any methods
 * in the port object. Doing so will cause a java.lang.IllegalStateException to be thrown.
 * 
 * 
 * 
 * AddEventListener
 * All the events received by this listener are generated by
 * one dedicated thread that belongs to the SerialPort object.
 * After the port is closed, no more event will be generated.
 * Another call to open() of the port's CommPortIdentifier object
 * will return a new CommPort object, and the lsnr has to be added
 * again to the new CommPort object to receive event from this port.
 * 
 * 
 * NotifyOnDataAvailable
 * Expresses interest in receiving notification when input data is available.
 * This may be used to drive asynchronous input. When data is available in the
 * input buffer, this event is propagated to the listener registered using addEventListener. 
 * The event will be generated once when new data arrive at the serial port. Even if
 * the user doesn't read the data, it won't be generated again until next time new data arrive
 * 
 * 
 * */