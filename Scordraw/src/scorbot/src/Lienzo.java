package scorbot.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javax.swing.JPanel;

public class Lienzo extends JPanel implements MouseMotionListener,MouseListener{
	private Point a, b;
	private boolean limpiar, nuevoTrazado;
	private ColaCircularConcurrente<LinkedList<Point>> trazos; 
	private LinkedList<Point> trazo;
	private int numMuestras; 
	
	public Lienzo(ColaCircularConcurrente<LinkedList<Point>> trazos) {
		a=new Point(-1, -1);
		b=new Point(-1, -1);
		numMuestras = 5;
		this.trazos = trazos;
		trazo = new LinkedList<Point>();
		setPreferredSize(new Dimension(360*2,180*2));
		//setMinimumSize(new Dimension(360*2,180*2));
		addMouseListener(this);
		addMouseMotionListener(this);
		repaint();
	}
	
	public int getNumMuestras() {
		return numMuestras;
	}

	public void setNumMuestras(int numMuestras) {
		this.numMuestras = numMuestras;
	}

	@Override
	public void paint(Graphics g) {
		validate();
		antialiasing(g);
		g.drawLine(a.x, a.y, b.x, b.y);
		if(limpiar){
			limpiar=false;
			super.paint(g);
		}
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		
		if(nuevoTrazado){
			pintarNuevoTrazo(g);
			nuevoTrazado=false;
		}
		
	}
	
	private void pintarNuevoTrazo(Graphics g) {
		g.setColor(Color.red);
		Iterator<Point> iterator = trazo.iterator();
		Point aux, aux2;
		if(!iterator.hasNext()){
			g.setColor(Color.black);
			return;
		}
		aux2=aux=iterator.next();
		for (; iterator.hasNext();) {
			aux2 = aux;
			aux = iterator.next();
			g.drawLine(aux2.x, aux2.y, aux.x, aux.y);
			
		}
		g.drawLine(aux2.x, aux2.y, aux.x, aux.y);
		g.setColor(Color.black);
		
	}

	/**
	 * Aplica un filtro antialiasing al objeto gráfico por parámetro
	 * @param g
	 */
	private void antialiasing(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	/**
	 * Limpia la pantalla
	 */
	public void limpiarPantalla() {
		limpiar=true;
		//repaint();
	}
	
	private void capturarPunto(Point p) {
		trazo.add((Point)p.clone());

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		trazo=new LinkedList<Point>();
		a.setLocation(e.getX(), e.getY());
		b.setLocation(e.getX(), e.getY());
		capturarPunto(b);	//Primer punto de la lista
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		new Thread(){
			@Override
			public void run() {
				trazo=limitarPuntos(trazo,numMuestras);
				trazos.encolar(trazo);
				nuevoTrazado=true;
				//repaint();
			}
		}.start();
		
		
		
		
		
		
	}
	
	
	public boolean isNuevoTrazado() {
		return nuevoTrazado;
	}

	public void setNuevoTrazado(boolean nuevoTrazado) {
		this.nuevoTrazado = nuevoTrazado;
	}

/**
 * Captura las coordenadas del raton y las encola en una lista enlazada
 */
	@Override
	public void mouseDragged(MouseEvent e) {
		a=(Point) b.clone();
		b.setLocation(e.getX(), e.getY());	//Modificar valores de b, para pintar una rectadesde a hasta b
		capturarPunto(b);	//Añadir a la lista de puntos que forma un trazo
		//repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private LinkedList<Point> limitarPuntos(LinkedList<Point> trazo, int limite) {
		LinkedList<Point> nuevoTrazo;
		int tamanoTrazo=0;
		if(trazo.size()>limite){
			nuevoTrazo = new LinkedList<Point>();
			tamanoTrazo = trazo.size();
			for (int i = 0; i < limite && !trazo.isEmpty(); i++) {
				nuevoTrazo.add(trazo.removeFirst());
				
				for (int j = 0; j < (int)(tamanoTrazo/limite)+1 && !trazo.isEmpty(); j++) {
					trazo.removeFirst();
					if(trazo.size()==1){
						nuevoTrazo.add(trazo.removeFirst());
						return nuevoTrazo;
					}
					
				}
				
			}
			if(trazo.size()==1){
				nuevoTrazo.add(trazo.removeFirst());
			}
			
			return nuevoTrazo;
		}
		
		return (LinkedList<Point>)trazo.clone();

	}
}
