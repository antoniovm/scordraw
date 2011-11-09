package scorbot.src;

public class ACLParser {
	
	public ACLParser() {
		
	}
	
	public String pinza(String estado) {
		if(estado.equals("A"))
			return "OPEN "+'\r';
		if(estado.equals("C"))
			return "CLOSE "+'\r';
		return "";
	}
	
	public static String mover(int x, int y, int z, int p, int r) {
		return "DEFP 10"+'\r'
		+"TEACH 10"+'\r'
		+x+'\r'
		+y+'\r'
		+z+'\r'
		+p+'\r'
		+r+'\r'
		+'\r'
		+"MOVEL 10"+'\r';

	}
}
