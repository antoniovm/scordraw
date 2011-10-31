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
	private JButton boton;
	
	public Interfaz() {
		inicializar();
	}

	private void inicializar() {
		lienzo = new Lienzo();
		consola = new JTextArea(">",20,10);
		consola.setLineWrap(true);
		
		scrollConsola = new JScrollPane(consola);
		scrollConsola.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		boton = new JButton("Abrir Pinza");
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				prompt("OPEN \r\n>");				
			}
		});
		
		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(lienzo);
		getContentPane().add(boton);
		getContentPane().add(scrollConsola);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void prompt(String s) {
		consola.append(s);

	}
}
