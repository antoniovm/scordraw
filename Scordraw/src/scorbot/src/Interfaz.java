package scorbot.src;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Interfaz extends JFrame{
	private JScrollPane scrollConsola;
	private Lienzo lienzo;
	private JTextArea consola;
	private JButton pinza, limpiar;
	
	public Interfaz() {
		inicializar();
		setVisible(true);
	}

	private void inicializar() {
		lienzo = new Lienzo();
		consola = new JTextArea(">",20,10);
		consola.setLineWrap(true);
		
		scrollConsola = new JScrollPane(consola);
		scrollConsola.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pinza = new JButton("Abrir Pinza");
		limpiar = new JButton("Limpiar");
		
		limpiar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lienzo.limpiarPantalla();
				
			}
		});
		
		pinza.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				prompt("OPEN \r\n>");				
			}
		});
		
		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(lienzo);
		getContentPane().add(pinza);
		getContentPane().add(limpiar);
		getContentPane().add(scrollConsola);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void prompt(String s) {
		consola.append(s);

	}
}
