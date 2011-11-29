package scorbot.src;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Interfaz extends JFrame{
	private JScrollPane scrollConsola;
	private Lienzo lienzo;
	private JTextArea consola;
	private JButton pinza, limpiar;
	private JComboBox cb;
	private JProgressBar pb;
	private Integer progreso;
	
	public Interfaz(ColaCircularConcurrente<LinkedList<Point>> trazos, Integer progreso) {
		this.progreso = progreso;
		inicializar(trazos);
		setVisible(true);
	}

	private void inicializar(ColaCircularConcurrente<LinkedList<Point>> trazos) {
		lienzo = new Lienzo(trazos);
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
		pb = new JProgressBar(0, 100);
		initCombobox();
		cb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lienzo.setNumMuestras((Integer)cb.getSelectedItem());
				
			}
		});
		
		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(lienzo);
		getContentPane().add(pinza);
		getContentPane().add(limpiar);
		getContentPane().add(cb);
		getContentPane().add(scrollConsola);
		getContentPane().add(pb);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pb.setValue(50);
		
	}
	public void initCombobox() {
		Integer [] numeros=new Integer[16];
		for (int i = 0; i < numeros.length; i++) {
			numeros[i]=i+5;
		}
		cb=new JComboBox(numeros);

	}
	public void prompt(String s) {
		consola.append(s);

	}
}
