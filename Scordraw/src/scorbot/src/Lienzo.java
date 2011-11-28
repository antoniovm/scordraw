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
	
	public Lienzo(ColaCircularConcurrente<LinkedList<Point>> trazos) {
		a=new Point(-1, -1);
		b=new Point(-1, -1);
		this.trazos = trazos;
		trazo = new LinkedList<Point>();
		setPreferredSize(new Dimension(700,500));
		addMouseListener(this);
		addMouseMotionListener(this);
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		antialiasing(g);
		g.drawLine(a.x, a.y, b.x, b.y);
		if(limpiar){
			super.paint(g);
			limpiar=false;
		}
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		
		if(nuevoTrazado){
			pintarNuevoTrazo(g);
			nuevoTrazado=false;
		}
		
	}
	
	private void pintarNuevoTrazo(Graphics g) {
		g.setColor(Color.red);
		Iterator<Point> iterator = limitarPuntos(trazos.ultimo()).iterator();
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
	 * Aplica un filtro antialiasing al objeto gr�fico por par�metro
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
		repaint();
	}
	
	private void capturarTrazado(Point p) {
		trazo.add(p);

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
		a.setLocation(e.getX(), e.getY());
		b.setLocation(e.getX(), e.getY());
		capturarTrazado((Point) b.clone());	//Primer punto de la lista
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		trazos.encolar(trazo);
		trazo=new LinkedList<Point>();
		nuevoTrazado=true;
		repaint();
		
		
	}
/**
 * Captura las coordenadas del raton y las encola en una lista enlazada
 */
	@Override
	public void mouseDragged(MouseEvent e) {
		a=(Point) b.clone();
		b.setLocation(e.getX(), e.getY());	//Modificar valores de b, para pintar una rectadesde a hasta b
		trazo.add((Point) b.clone());	//A�adir a la lista de puntos que forma un trazo
		repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private LinkedList<Point> limitarPuntos(LinkedList<Point> trazo) {
		LinkedList<Point> nuevoTrazo;
		if(trazo.size()>20){
			nuevoTrazo = new LinkedList<Point>();
			for (int i = 0; i < 20; i++) {
				nuevoTrazo.add(trazo.removeFirst());
				
				for (int j = 0; j < trazo.size()/20+1; j++) {
					trazo.removeFirst();
					if(trazo.size()<2){
						nuevoTrazo.add(trazo.removeFirst());
						return nuevoTrazo;
					}
				}
				
			}
			
			return nuevoTrazo;
		}
		
		return trazo;

	}
}
