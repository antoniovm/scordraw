package scorbot.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


import javax.swing.JPanel;

public class Lienzo extends JPanel{

	public Lienzo() {
		setPreferredSize(new Dimension(500,500));
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(0xDDDDDD));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.black);
		g.drawString("Zona de dibujado", this.getWidth()/2, this.getHeight()/2);
		
	}
}
