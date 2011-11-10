package scorbot.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import javax.swing.JPanel;

public class Lienzo extends JPanel implements MouseMotionListener,MouseListener{
	private boolean pulsado;
	private Point a, b;
	private boolean limpiar;
	
	
	public Lienzo() {
		a=new Point(-1, -1);
		b=new Point(-1, -1);
		setPreferredSize(new Dimension(500,500));
		addMouseListener(this);
		addMouseMotionListener(this);
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		if(limpiar)
			super.paint(g);
		g.setColor(new Color(0xDDDDDD));
		g.setColor(Color.black);
		g.drawLine(a.x, a.y, b.x, b.y);
		if(limpiar){
			super.paint(g);
			limpiar=false;
		}
		
		
	}
	
	public void limpiarPantalla() {
		limpiar=true;
		repaint();

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
		pulsado = true;
		a.setLocation(e.getX(), e.getY());
		b.setLocation(e.getX(), e.getY());
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		a=(Point) b.clone();
		b.setLocation(e.getX(), e.getY());
		repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
