package scorbot.src;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Interfaz extends JFrame implements WindowListener, ActionListener{
	private JScrollPane scrollConsola;
	private Lienzo lienzo;
	private JTextArea consola;
	private JButton pinza, limpiar;
	private JComboBox cb;
	private JProgressBar pb;
	private JMenuBar barra;
	private JMenu mArchivo, mAyuda;
	private JMenuItem iSalir, iAbout;
	private JDialog dAbout;
	private Scorbot scb;
	private JLabel lProgreso, lNumeroMuestras;
	
	public Interfaz(ColaCircularConcurrente<LinkedList<Point>> trazos, Scorbot scb) {
		this.scb = scb;
		inicializar(trazos);
		addWindowListener(this);
		setVisible(true);
	}

	private void inicializar(ColaCircularConcurrente<LinkedList<Point>> trazos) {
		lienzo = new Lienzo(trazos);
		consola = new JTextArea(">",20,20);
		consola.setLineWrap(true);
		pb = scb.getProgreso();
		scrollConsola = new JScrollPane(consola);
		scrollConsola.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pinza = new JButton("Abrir Pinza");
		limpiar = new JButton("Limpiar");
		
		setTitle("Scordraw");
		
		//MENUBAR
		barra = new JMenuBar();
		mArchivo = new JMenu("Archivo");
		mAyuda = new JMenu("Ayuda");
		iSalir = new JMenuItem("Salir");
		iAbout = new JMenuItem("Acerca de");
		
		mArchivo.add(iSalir);
		mAyuda.add(iAbout);
		barra.add(mArchivo);
		barra.add(mAyuda);
		
		setJMenuBar(barra);
		
		iSalir.addActionListener(this);
		iAbout.addActionListener(this);
		
		dAbout = new JDialog(this, "Acerca de Scordraw", true);
		
		
		lProgreso = new JLabel("Esperando nuevo trazo...");
		lNumeroMuestras = new JLabel("Seleccione numero de muestras:");
		GridBagConstraints constraints = new GridBagConstraints();
		
		
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
	
		pb.setValue(0);
		
		initCombobox();
		cb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lienzo.setNumMuestras((Integer)cb.getSelectedItem());
				
			}
		});
		
		getContentPane().setLayout(new GridBagLayout());
		
		getContentPane().add(lienzo, formato(0,0,4,2,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(1,1,1,1)));
		getContentPane().add(pinza, formato(0,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(10,10,10,10)));
		getContentPane().add(limpiar, formato(1,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(10,10,10,10)));
		getContentPane().add(lNumeroMuestras, formato(2,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(10,10,10,10)));
		getContentPane().add(cb, formato(3,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(10,10,10,10)));
		getContentPane().add(scrollConsola, formato(4,0,1,5,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(1,1,1,1)));
		getContentPane().add(lProgreso, formato(0,3,4,1,GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(10,10,10,10)));
		getContentPane().add(pb,formato(0,4,4,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL, new Insets(10,10,10,10)));
		pack();
		
		
		
	}
	private GridBagConstraints formato(int x, int y, int width, int height, int anchor, int fill, Insets insets) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor=anchor;
		constraints.fill=fill;
		constraints.gridheight=height;
		constraints.gridwidth=width;
		constraints.gridx=x;
		constraints.gridy=y;
		constraints.insets=insets;
		return constraints;
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

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("Cerrando...");
		scb.cerrar();
		System.exit(1);
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == iSalir) {
			System.exit(0);
		}
		if (e.getSource() == iAbout) {
			dAbout.setVisible(true);
		}
		
	}
}
