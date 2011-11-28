package scorbot.src;

import java.awt.Point;

public class ConversorCoordenadas {
	
	public static Point convertir(Point pLienzo){
		//escalar
		int xReal = (int) (pLienzo.getX()*7000/700);
		int yReal = (int) (pLienzo.getY()*5000/500);
		
		//desplazar el origen
		xReal -= 35;
		yReal -= 15;
		
		return new Point(xReal, yReal);
		
	}

}
