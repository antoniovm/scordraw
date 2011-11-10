package scorbot.src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javax.swing.JPanel;

public class Lienzo extends JPanel implements MouseMotionListener,MouseListener{
	private Point a, b;
	private boolean limpiar;
	private ColaCircularConcurrente<LinkedList<Point>> trazos; 
	private LinkedList<Point> trazo;
	
	public Lienzo(ColaCircularConcurrente<LinkedList<Point>> trazos) {
		a=new Point(-1, -1);
		b=new Point(-1, -1);
		this.trazos = trazos;
		trazo = new LinkedList<Point>();
		setPreferredSize(new Dimension(500,500));
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
		
	}
/**
 * Captura las coordenadas del raton y las encola en una lista enlazada
 */
	@Override
	public void mouseDragged(MouseEvent e) {
		a=(Point) b.clone();
		trazo.add((Point) b.clone());	//Añadir a la lista de puntos que forma un trazo
		b.setLocation(e.getX(), e.getY());	//Modificar valores de b, para pintar una rectadesde a hasta b
		repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
