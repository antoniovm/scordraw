package scorbot.src;

import java.awt.Point;

public class ConversorCoordenadas {
	
	public static Point convertir(Point pLienzo){
		//escalar
		int xReal = (int) (pLienzo.getX()*3600/360);
		int yReal = (int) (pLienzo.getY()*1800/180);
		
		//desplazar el origen
		xReal -= 1800;
		yReal += 1500;
		System.out.println("Lienzo: ("+pLienzo.x +  ", " + pLienzo.y+")");
		System.out.println("Mesa: ("+xReal +  ", " + -yReal+")");
		return new Point(xReal, -yReal);
		
	}

}
