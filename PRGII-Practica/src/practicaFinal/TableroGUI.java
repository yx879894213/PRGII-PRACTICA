package practicaFinal;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class TableroGUI {

	static JFrame frame;
	static JMenuItem mntmSalir;
	static Casilla[][] tablero;
	protected Casilla[] tren;
	protected int ID=1;
	protected int capacity = 1;
	protected int trenNum = 0;
	protected JLabel coord;
	protected int fila;
	protected int col;
	protected JMenu mnRealizarSimulacion;
	protected JMenuItem mntmMover;
	protected JMenuItem mntmPlay;
	protected JMenuItem mntmStop;
	protected JMenuItem mntmAyuda;
	protected JMenuItem mntmBorrar;
	protected JMenuItem mntmAadir;
	protected JMenuItem mntmDeshacer;
	protected JMenuItem mntmRehacer;
	protected JMenuItem mntmNuevo;
	protected JMenuItem mntmCargar;
	protected JMenuItem mntmGrabar;
	public TableroGUI(int fila, int col) {
		super();

		this.fila = fila;
		this.col = col;
		tren = new Casilla[capacity];
		Tabla();
		this.showBoard();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void Tabla() {
		frame = new JFrame();
		frame.setTitle("Trenecitos");
		frame.setBounds(100, 100, 200+20*col, 200+20*fila);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		mntmNuevo = new JMenuItem("Nuevo");
		mnArchivo.add(mntmNuevo);
		
		mntmCargar = new JMenuItem("Cargar");
		mnArchivo.add(mntmCargar);
		
		mntmGrabar = new JMenuItem("Grabar");
		mnArchivo.add(mntmGrabar);
		
		mntmSalir = new JMenuItem("Salir");
		mnArchivo.add(mntmSalir);
		
		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
		
		mntmDeshacer = new JMenuItem("Deshacer");
		mnEditar.add(mntmDeshacer);
		
		mntmRehacer = new JMenuItem("Rehacer");
		mnEditar.add(mntmRehacer);
		
		mntmAadir = new JMenuItem("añadir");

		mnEditar.add(mntmAadir);
		
		mntmBorrar = new JMenuItem("borrar");
		mnEditar.add(mntmBorrar);
		
		mnRealizarSimulacion = new JMenu("Realizar simulacion");
		menuBar.add(mnRealizarSimulacion);
		
		mntmMover = new JMenuItem("Mover");
		mnRealizarSimulacion.add(mntmMover);
		
		mntmPlay = new JMenuItem("PLAY");
		mnRealizarSimulacion.add(mntmPlay);
		
		mntmStop = new JMenuItem("STOP");
		mntmStop.setEnabled(false);
		mnRealizarSimulacion.add(mntmStop);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		mntmAyuda = new JMenuItem("Ayuda");
		mnAyuda.add(mntmAyuda);
		frame.getContentPane().setLayout(new GridLayout(fila+1, col+1, 0, 0));
		
	}
	
	public void showBoard(){
		
		tablero = new Casilla[fila][col];
		/**
		 * coordenadas de las columnas
		 */
		coord = new JLabel("");
		frame.getContentPane().add(coord);
		for(int j=0; j<col; j++) {
			coord = new JLabel(Integer.toString(j));
			frame.getContentPane().add(coord);
		}
		/**
		 * coordenadas de las filas y las casillas
		 */		
		for(int i=fila-1; i>=0; i--) {
			coord = new JLabel(Integer.toString(i));
			frame.getContentPane().add(coord);
			for(int j=0; j<col; j++) {
				tablero[j][i] = new Casilla(j,i);
				tablero[j][i].setBackground(Color.LIGHT_GRAY);
				tablero[j][i].setVisible(false);
				frame.getContentPane().add(tablero[j][i]);
			    tablero[j][i].setToolTipText("("+j+","+i+")");
			}
		}
	}
	
	
}
