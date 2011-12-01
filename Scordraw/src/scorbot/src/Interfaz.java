package scorbot.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;


public class Interfaz extends JFrame implements WindowListener, ActionListener{
	
	private JScrollPane sbConsola;
	private Lienzo pLienzo;
	private JTextArea consola;
	private JButton bAbortar, bLimpiarLienzo,bLimpiarConsola, bHome, bCogerPincel, bCon, bCoff;
	private JComboBox cbMuestras;
	private JProgressBar pbAlmacenarPos;
	private JMenuBar barra;
	private JMenu mArchivo, mAyuda;
	private JMenuItem iSalir, iAbout;
	private JDialog dAbout;
	private Scorbot scb;
	private JLabel lProgreso, lNumeroMuestras;
	
	public Interfaz(ColaCircularConcurrente<LinkedList<Point>> trazos, Scorbot scb) {
		try
		{
		   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		   e.printStackTrace();
		}
		this.scb = scb;
		inicializar(trazos);
		addWindowListener(this);
		setVisible(true);
		setResizable(false);
	}

	private void inicializar(ColaCircularConcurrente<LinkedList<Point>> trazos) {
		pLienzo = new Lienzo(trazos);
		(consola = new JTextArea(">",20,30)).setEditable(false);
		consola.setLineWrap(true);
		consola.setPreferredSize(consola.getSize());
		//consola.setMinimumSize(consola.getSize());
		sbConsola = new JScrollPane(consola);
		sbConsola.setAutoscrolls(true);
		sbConsola.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//BOTONES
		bAbortar = new JButton("Abortar");
		bAbortar.setBackground(new Color(0x00ff5555));
		bAbortar.setForeground(Color.black);
		bLimpiarLienzo = new JButton("Limpiar lienzo");
		bLimpiarConsola = new JButton("Limpiar consola");
		bCon = new JButton("Ctrl.ON");
		bCoff = new JButton("Ctrl.OFF");
		bHome = new JButton("Home");
		bHome.setBackground(Color.green);
		bCogerPincel = new JButton("Coger pincel");
		
		
		setTitle("Scordraw");
		
		ImageIcon img = new ImageIcon("."+File.separator+"bin"+File.separator+"img"+File.separator+"logo2.png");
		setIconImage(img.getImage());
		
		
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
		
		lProgreso = new JLabel("Esperando a nuevo trazo...");
		lNumeroMuestras = new JLabel("Seleccione numero de muestras:");
		GridBagConstraints constraints = new GridBagConstraints();
		
		
		pbAlmacenarPos = new JProgressBar(0,100){
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setFont(new Font(g.getFont().getFontName(), 0, 20));
				FontMetrics fm =g.getFontMetrics();
				int i=fm.stringWidth(this.getValue()/this.getMaximum()*100+"%");
				g.setColor(Color.black);
				g.drawString(this.getValue()+"%", getWidth()/2-i/2, getHeight()/2+7);
				
			}
		};
		pbAlmacenarPos.setPreferredSize(new Dimension(pbAlmacenarPos.getWidth(), 30));
		
		bLimpiarLienzo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pLienzo.limpiarPantalla();
				
			}
		});
		
		bAbortar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pbAlmacenarPos.setValue(0);
				lProgreso.setText("Abortado.");
				scb.abortar();				
			}
		});
		
		bHome.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bAbortar.setEnabled(false);
				pbAlmacenarPos.setValue(0);
				lProgreso.setText("Volviendo a inicio...");
				
				new Thread(){
					public void run() {
						scb.home();
						scb.controlOn();
						scb.declararPosicion("H");
						scb.guardarPosicionAbsoluta("H", 0, -2500, 1000, -900, 0);
						scb.mover("H");
						lProgreso.setText("Terminado!");
						bAbortar.setEnabled(true);
					};
				}.start();	
				
			}
		});
		
		bCogerPincel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(){
					public void run() {
						scb.cogerPincel();
					}
				}.start();	
				
			}
		});
	
		pbAlmacenarPos.setValue(0);
		
		initCombobox();
		cbMuestras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pLienzo.setNumMuestras((Integer)cbMuestras.getSelectedItem());
				
			}
		});
		
		getContentPane().setLayout(new GridBagLayout());
		
		getContentPane().add(pLienzo, formato(0,0,6,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(1,1,1,1)));
		getContentPane().add(bAbortar, formato(0,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(10,10,2,2)));
		getContentPane().add(bHome, formato(1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(10,2,2,2)));
		getContentPane().add(bCogerPincel, formato(2,1,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(10,2,2,10)));
		getContentPane().add(bLimpiarLienzo, formato(0,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(2,10,10,2)));
		getContentPane().add(bLimpiarConsola, formato(1,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(2,2,10,2)));
		getContentPane().add(bCon, formato(2,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(2,2,10,2)));
		getContentPane().add(bCoff, formato(3,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(2,2,10,10)));
		getContentPane().add(lNumeroMuestras, formato(4,1,1,2,GridBagConstraints.EAST,GridBagConstraints.NONE, new Insets(10,10,10,10)));
		getContentPane().add(cbMuestras, formato(5,1,1,2,GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(10,2,10,10)));
		getContentPane().add(sbConsola, formato(6,0,1,5,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(1,1,1,1)));
		getContentPane().add(lProgreso, formato(0,3,6,1,GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(10,10,10,10)));
		getContentPane().add(pbAlmacenarPos,formato(0,4,6,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL, new Insets(10,10,10,10)));
		pack();
		
		configurarDialog();
		
		
	}
	public JButton getbAbortar() {
		return bAbortar;
	}

	public void setbAbortar(JButton bAbortar) {
		this.bAbortar = bAbortar;
	}

	public JButton getbLimpiar() {
		return bLimpiarLienzo;
	}

	public void setbLimpiar(JButton bLimpiar) {
		this.bLimpiarLienzo = bLimpiar;
	}

	private void configurarDialog() {
		JTextArea texto = new JTextArea("Scordraw v Alpha1 \n\nDesarrolladores:\nAntonio Vicente Martín\nJorge García Hinestrosa\n\nControl y programación de Robots\n3º ITIS - Universidad de Almería\n2011");
		texto.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		texto.setEditable(false);
		
		JPanel panelImagen = new JPanel(){
			public void paint(Graphics g) {
				ImageIcon img = new ImageIcon("."+File.separator+"bin"+File.separator+"img"+File.separator+"logo2.png");
				g.drawImage(img.getImage(), 0, 0, this);
			}
		};
		
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBackground(Color.white);
		panelSuperior.setLayout(new GridBagLayout());
		//panelSuperior.add(new JButton("IMAGEN"), formato(0,0,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(2,2,2,2)));
		panelSuperior.add(panelImagen, formato(0,0,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(2,7,2,-120)));
		panelSuperior.add(texto, formato(1,0,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(2,120,2,2)));
		
		JPanel panelInferior = new JPanel();
		JButton boton=new JButton("Aceptar");
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dAbout.dispose();
				
			}
		});
		panelInferior.setLayout(new GridBagLayout());
		panelInferior.add(boton, formato(0,0,2,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(2,2,2,2)));
		
		dAbout.setLayout(new GridBagLayout());
		dAbout.add(panelSuperior,formato(0,0,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(0, 0, 2, 0)));
		dAbout.add(panelInferior,formato(0,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(2,2,2,2)));
		
		//dAbout.add(new JButton("IMAGEN"));
		//dAbout.add(texto);
		dAbout.pack();
		Dimension dPantalla, dVentana;
		dPantalla = this.getSize(); // Dimensiones en pixels de la pantalla.
        dVentana = dAbout.getSize(); // Dimensiones en pixels de la ventana.
        // Situamos la ventana en el centro de la pantalla.
        dAbout.setLocation((dPantalla.width - dVentana.width) / 2, (dPantalla.height - dVentana.height) / 2);
		
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
		cbMuestras=new JComboBox(numeros);

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

	public JScrollPane getScrollConsola() {
		return sbConsola;
	}

	public void setScrollConsola(JScrollPane scrollConsola) {
		this.sbConsola = scrollConsola;
	}

	public Lienzo getLienzo() {
		return pLienzo;
	}

	public void setLienzo(Lienzo lienzo) {
		this.pLienzo = lienzo;
	}

	public JTextArea getConsola() {
		return consola;
	}

	public void setConsola(JTextArea consola) {
		this.consola = consola;
	}

	public JButton getAbortar() {
		return bAbortar;
	}

	public void setAbortar(JButton abortar) {
		this.bAbortar = abortar;
	}

	public JButton getLimpiar() {
		return bLimpiarLienzo;
	}

	public void setLimpiar(JButton limpiar) {
		this.bLimpiarLienzo = limpiar;
	}

	public JComboBox getCb() {
		return cbMuestras;
	}

	public void setCb(JComboBox cb) {
		this.cbMuestras = cb;
	}

	public JProgressBar getPb() {
		return pbAlmacenarPos;
	}

	public void setPb(JProgressBar pb) {
		this.pbAlmacenarPos = pb;
	}

	public JMenuBar getBarra() {
		return barra;
	}

	public void setBarra(JMenuBar barra) {
		this.barra = barra;
	}

	public JMenu getmArchivo() {
		return mArchivo;
	}

	public void setmArchivo(JMenu mArchivo) {
		this.mArchivo = mArchivo;
	}

	public JMenu getmAyuda() {
		return mAyuda;
	}

	public void setmAyuda(JMenu mAyuda) {
		this.mAyuda = mAyuda;
	}

	public JMenuItem getiSalir() {
		return iSalir;
	}

	public void setiSalir(JMenuItem iSalir) {
		this.iSalir = iSalir;
	}

	public JMenuItem getiAbout() {
		return iAbout;
	}

	public void setiAbout(JMenuItem iAbout) {
		this.iAbout = iAbout;
	}

	public JDialog getdAbout() {
		return dAbout;
	}

	public void setdAbout(JDialog dAbout) {
		this.dAbout = dAbout;
	}

	public Scorbot getScb() {
		return scb;
	}

	public void setScb(Scorbot scb) {
		this.scb = scb;
	}

	public JLabel getlProgreso() {
		return lProgreso;
	}

	public void setlProgreso(JLabel lProgreso) {
		this.lProgreso = lProgreso;
	}

	public JLabel getlNumeroMuestras() {
		return lNumeroMuestras;
	}

	public void setlNumeroMuestras(JLabel lNumeroMuestras) {
		this.lNumeroMuestras = lNumeroMuestras;
	}
	
}
