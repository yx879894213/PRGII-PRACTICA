package practicaFinal;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

//public JFrame frame;
//protected JMenuItem mntmSalir;
//protected Casilla[][] tablero;
//protected Casilla[] tren;
//protected int ID=1;
//protected int capacity = 1;
//protected int trenNum = 0;
//protected JLabel coord;
//protected int fila;
//protected int col;
//protected JMenu mnRealizarSimulacion;
//protected JMenuItem mntmMover;
//protected JMenuItem mntmPlay;
//protected JMenuItem mntmAyuda;
//protected JMenuItem mntmBorrar;
//protected JMenuItem mntmAadir;
//protected JMenuItem mntmDeshacer;
//protected JMenuItem mntmRehacer;
//protected JMenuItem mntmNuevo;
//protected JMenuItem mntmCargar;
//protected JMenuItem mntmGrabar;

public class TableroImplement extends TableroGUI{
	Timer time = new Timer();
	TimerTask task;
	Originator originator = new Originator();
	CareTaker caretaker = new CareTaker();
	int estado = -1;
	
	public TableroImplement(int fila, int col) {
		super(fila, col);
		this.accionPlay();
		this.accionAniadir();
		this.accionSalir();
		this.accionMover();
		this.accionBorrar();
		this.accionNuevo();
		this.accionAyuda();
		this.accionStop();
		this.accionDeshacer();
		OyenteCasilla oyente = new OyenteCasilla();
		for(int i=0; i<fila; i++)
			for(int j=0; j<col; j++)
				tablero[j][i].addActionListener(oyente);

	}
	
	private void accionPlay() {
		mntmPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				task = new TimerTask() {
					public void run() {
						if(!end()) {
						mntmPlay.setEnabled(false);
						mntmStop.setEnabled(true);
						movTrenes();
						}else {
							mnRealizarSimulacion.setEnabled(true);
							mntmPlay.setEnabled(true);
							mntmStop.setEnabled(false);
							this.cancel();
						}
					}
				};
				time.scheduleAtFixedRate(task,500,500);		
				
			}
		});
	}
	
	private void accionStop() {
		mntmStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				task.cancel();
				mntmPlay.setEnabled(true);
				mntmStop.setEnabled(false);
			}
		});
	}
	
	private void accionAniadir() {
		mntmAadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean numTrenCorrecto = false;
				int numeroTren = 0;
				while(!numTrenCorrecto) {
					numeroTren = Integer.parseInt(JOptionPane.showInputDialog("Introduce el numero de los trenes que deseas meter."));
					if(numeroTren < 0 || numeroTren > fila*col)
						JOptionPane.showMessageDialog(null, "Numero incorrecto","Warning", JOptionPane.WARNING_MESSAGE, null );
					else{
						numTrenCorrecto = true;
					}
				}

				for(int i = 0; i<numeroTren;i++) {
					boolean correcto = false;
					while(!correcto) {
						String[] dato = new String[4];
						String datos = JOptionPane.showInputDialog("Introduce los datos del tren"+(trenNum+1)+"º (Sentido Longitud CoorX CoorY)");
						dato = datos.split(" ");
						correcto = addTren(dato[0], Integer.parseInt(dato[1]), Integer.parseInt(dato[2]), Integer.parseInt(dato[3]));
						if(!correcto)
							JOptionPane.showMessageDialog(null, "Datos incorrecto","Warning", JOptionPane.WARNING_MESSAGE, null );
					}
				}
			}
		});
	}
	
	private void accionSalir() {
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	private void accionMover() {
		originator.setState(this);
		caretaker.add(originator.guardar());
		estado++;
		mntmMover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				movTrenes();
			}
		});
	}
	
	private void accionBorrar() {
		
		mntmBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int ID;
				ID = Integer.parseInt(JOptionPane.showInputDialog("Introduce el ID del tren que deseas eliminar "
						+ "\n(primer tren que aniade tiene ID=1, segundo ID=2, sucesivamente.)"));
				if(ID < 1 || ID > trenNum)
					JOptionPane.showMessageDialog(null, "Numero incorrecto","Warning", JOptionPane.WARNING_MESSAGE, null );
				else {
					if(borraTren(ID))
						JOptionPane.showMessageDialog(null, "Tren "+ID+"ºborrado","Exito", JOptionPane.OK_OPTION, null );
					else
						JOptionPane.showMessageDialog(null, "Tren "+ID+"º no existe","No existe", JOptionPane.OK_OPTION, null );

				}
			}
		});
		
	}

	private void accionNuevo() {
		mntmNuevo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean tamCorrecto = false;
				int fila = 0, col = 0;
				while(!tamCorrecto) {
					String tam = JOptionPane.showInputDialog("Introduce el numero de fila y columna separado por espacio.");
					String[] filcol = new String[2];
					filcol = tam.split(" ");
					fila = Integer.parseInt(filcol[0]);
					col = Integer.parseInt(filcol[1]);
					if((fila >= 10 && fila <= 30) && (col >= 10 && col <= 30))
						tamCorrecto = true;
					else
						JOptionPane.showMessageDialog(null, "El numero de la fila y de columna debe estar entre [10,30] .","Error",JOptionPane.ERROR_MESSAGE, null );
				}
				Main.tablero.frame.setVisible(false);
				Main.tablero = new TableroImplement(fila,col );
				Main.tablero.frame.setVisible(true);
				Main.tablero.showBoard();
				
			}
		});
	}

	private boolean borraTren(int ID) {
		boolean salida = true, encontrado = false;;
		int i = 0;
		while(i<trenNum && !encontrado) {
			if(tren[i].getTrenID() == ID)
				encontrado = true;
			else
				i++;
		}
		
		if(!encontrado){
			salida = false;
		}
		else{
			int sentido = tren[i].getSentido();
			int longitud = tren[i].getLongitud();
			int x = tren[i].getCoorX();
			int y = tren[i].getCoorY();
			
			tren[i].setLongitud(0);
			tren[i].setSentido(-1);
			tren[i].setTrenID(0);
			tren[i].setCabeza(false);
			
			switch (sentido) {		//0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
			case 0:
				for(i=0; i<longitud; i++) {
					tablero[x][y+i].setBackground(Color.LIGHT_GRAY);
					tablero[x][y+i].setVisible(false);
				}
				break ;
			case 1:
				for(i=0; i<longitud; i++) {
					tablero[x][y-i].setBackground(Color.LIGHT_GRAY);
					tablero[x][y-i].setVisible(false);
				}
				break ;
			case 2:
				for(i=0; i<longitud; i++) {
					tablero[x+i][y].setBackground(Color.LIGHT_GRAY);
					tablero[x+i][y].setVisible(false);
	
				}
				break ;
			default:
				for(i=0; i<longitud; i++) {
					tablero[x-i][y].setBackground(Color.LIGHT_GRAY);
					tablero[x-i][y].setVisible(false);
				}
			}
		}
		
		return salida;
	}
	
	private void accionAyuda() {
		mntmAyuda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "---Archivo---"
						+ "\nNuevo: Crea un tablero nuevo."
						+ "\nCargar: Carga el el juego que ha guardado."
						+ "\nGrabar: Graba el estado del juego en un fichero."
						+ "\nSalir: Se cierre el juego."
						+ "\n---Editar---"
						+ "\nDeshacer: Deshace un paso."
						+ "\nRehacer: Rehace un paso."
						+ "\nAñadir: Se anñade los trenes."
						+ "\nBorrar: Se borra un tren introduciendo el ID del tren.(ID = 1,2,3...)"
						+ "\n---Realizar Simulacion---"
						+ "\nMover: Se mueven los trenes un paso."
						+ "\nPlay: Se simula el juego hasta que no quede ningun tren en el tablero.");
			}
		});
	}
		
	private void accionDeshacer() {
		mntmDeshacer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				originator.restaurar(caretaker.getMemento(estado));
				
			}
		});
		if(estado > 0 )
			estado--;
	}
	
	public class OyenteCasilla implements ActionListener{
		public void actionPerformed (ActionEvent e)
	  {
			if(e.getSource() instanceof Casilla ) {
				Casilla pulsado = (Casilla) e.getSource(), aux;
				if(pulsado.getBackground() != Color.BLACK && pulsado.getBackground() != Color.LIGHT_GRAY) {
					aux=buscaCabeza(pulsado.getCoorX(), pulsado.getCoorY());
					int numero = -1;
					for(int i=0; i<trenNum && numero == -1; i++) {
						if(tren[i].getTrenID() == aux.getTrenID())
							numero = i;
					}
					movTren(numero);
				}
			}
	  }
	}

	private boolean end() {
		boolean salida = true;
		for(int i=0; i< trenNum; i++) {
			if(tren[i].isCabeza()) {
				salida = false;
			}
		}
		return salida;
	}
	
	public boolean addTren(String sentido, int longitud, int coorX, int coorY) {
		
		boolean addExito = this.addTrenCorrecto(sentido, longitud, coorX, coorY);
		if(addExito) {
			if(trenNum==capacity)
				this.expandCapacity();
			tren[trenNum] = tablero[coorX][coorY];
			tren[trenNum].setSentido(sentido);
			tren[trenNum].setLongitud(longitud);
			tren[trenNum].setCabeza(true);
			tren[trenNum].setTrenID(ID++);
			this.rellenaTablero(tren[trenNum]);
			trenNum++;
		}
		return addExito;
	}
	
	private boolean addTrenCorrecto(String sentido, int longitud, int coorX, int coorY) {
		boolean entradaCorrecto = true;
		int sentidoInt = -1;
		switch (sentido.toUpperCase()) {
		case "B":
			sentidoInt = 0;			break;
		case "A":
			sentidoInt = 1;			break;
		case "I":
			sentidoInt = 2;			break;
		case "D":
			sentidoInt = 3;			break;
		default:
			entradaCorrecto = false;
		}
		if(entradaCorrecto)
			switch (sentidoInt) {		//0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
			case 0:
				for(int i=0; i<longitud && entradaCorrecto == true; i++)
					if(!this.inTable(coorX, coorY+i) || tablero[coorX][coorY+i].isVisible())
						entradaCorrecto = false;
				break ;
			case 1:
				for(int i=0; i<longitud && entradaCorrecto == true; i++) 
					if(!this.inTable(coorX, coorY-i) || tablero[coorX][coorY-i].isVisible())
						entradaCorrecto = false;
				break ;
			case 2:
				for(int i=0; i<longitud && entradaCorrecto == true; i++)
					if(!this.inTable(coorX+i, coorY) || tablero[coorX+i][coorY].isVisible())
						entradaCorrecto = false;
				break ;
			default:
				for(int i=0; i<longitud && entradaCorrecto == true; i++) 
					if(!this.inTable(coorX-i, coorY) || tablero[coorX-i][coorY].isVisible())
						entradaCorrecto = false;
			}
		
		return entradaCorrecto;
	}

	private void expandCapacity() {
		capacity*=2;
		Casilla[] aux = new Casilla[capacity];
		for(int i=0; i<tren.length; i++)
			aux[i]=tren[i];
		tren = aux;
	}

	private void rellenaTablero(Casilla tren) {
		int sentido = tren.getSentido();
		int longitud = tren.getLongitud();
		int x = tren.getCoorX();
		int y = tren.getCoorY();
		switch (sentido) {		//0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
		case 0:
			for(int i=0; i<longitud; i++) {
					tablero[x][y+i].setBackground(Color.BLUE);
					tablero[x][y+i].setVisible(true);
			}
			break ;
		case 1:
			for(int i=0; i<longitud; i++) {
					tablero[x][y-i].setBackground(Color.GREEN);
					tablero[x][y-i].setVisible(true);
			}
			break ;
		case 2:
			for(int i=0; i<longitud; i++) {
					tablero[x+i][y].setBackground(Color.RED);
					tablero[x+i][y].setVisible(true);
			}
			break ;
		default:
			for(int i=0; i<longitud; i++) {
					tablero[x-i][y].setBackground(Color.YELLOW);
					tablero[x-i][y].setVisible(true);
			}
		}
	}
	
	private void ordenaTrenes() {
		
		Casilla[] trenesOrdenado = new Casilla[trenNum];
		int contador = 0;
		
		for(int j = 0; j< 4; j++) {
			for(int i = 0; i< trenNum; i++)
				if(tren[i].getSentido() == j)
					trenesOrdenado[contador++] = tren[i];
		}
		for(int i = 0; i < trenNum; i++)
			tren[i] = trenesOrdenado[i];	
	}
	
	private void mover(int x, int y) {
		//0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
		int sentido = -1;
		if(tablero[x][y].getBackground() == Color.BLUE)
			sentido = 0;
		else if(tablero[x][y].getBackground() == Color.GREEN)
			sentido = 1;
		else if(tablero[x][y].getBackground() == Color.RED)
			sentido = 2;
		else if(tablero[x][y].getBackground() == Color.YELLOW)
			sentido = 3;
		
		if(tablero[x][y].getBackground() != Color.BLACK) {
			tablero[x][y].setBackground(Color.LIGHT_GRAY);
			tablero[x][y].setVisible(false);
		
			switch(sentido) {
				case 0: 
					if(y-1 >= 0) 
						if(!tablero[x][y-1].isVisible()) {
							tablero[x][y-1].setBackground(Color.BLUE);
							tablero[x][y-1].setVisible(true);
						}
						else if(tablero[x][y-1].getBackground() != Color.BLACK)
							tablero[x][y-1].setBackground(Color.BLACK);
					break;
				case 1:
					if(y+1 < fila)
						if(!tablero[x][y+1].isVisible()) {
							tablero[x][y+1].setBackground(Color.GREEN); 
							tablero[x][y+1].setVisible(true);
						}
						else if(tablero[x][y+1].getBackground() != Color.BLACK)
							tablero[x][y+1].setBackground(Color.BLACK);
					break;
				case 2:
					if(x-1 >= 0) 
						if(!tablero[x-1][y].isVisible()) {
							tablero[x-1][y].setBackground(Color.RED);
							tablero[x-1][y].setVisible(true);
						}
						else if(tablero[x-1][y].getBackground() != Color.BLACK)
							tablero[x-1][y].setBackground(Color.BLACK);
					break;
				case 3:
					if(x+1 < col) 
						if(!tablero[x+1][y].isVisible()) {
							tablero[x+1][y].setBackground(Color.YELLOW);
							tablero[x+1][y].setVisible(true);
						}
						else if(tablero[x+1][y].getBackground() != Color.BLACK)
							tablero[x+1][y].setBackground(Color.BLACK);
					break;
			}
		}
	
	}

	public void movTren(int trenNum) {
		int longitud = tren[trenNum].getLongitud();
		int sentido = tren[trenNum].getSentido();
		int x = tren[trenNum].getCoorX();
		int y = tren[trenNum].getCoorY();
				
		if(tablero[x][y].isVisible()) {
			switch(sentido) {		//0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
			case 0:
				if(tablero[x][y].getBackground() == Color.BLACK) {		
					/*Si fuese chocado por otro tren.el coordenada Y se modifica por el anterior y la longitud del tren se disminuye 1.*/
					tren[trenNum].setLongitud(--longitud);
					if(tren[trenNum].getLongitud() == 0)
						tren[trenNum].setCabeza(false);
					else {
						this.newCabeza(trenNum,x,++y);
						this.movTren(trenNum);
					}
				}else {
					for(int j =0 ; j<longitud; j++) 
						mover(x, y+j);
					
					if(y-1 >= 0 && tablero[x][y-1].getBackground() != Color.BLACK) {
						this.newCabeza(trenNum, x, y-1);
					}else {
						tren[trenNum].setLongitud(--longitud);
						if(tren[trenNum].getLongitud() == 0)
							tren[trenNum].setCabeza(false);
					}
				}
				
				break;
			case 1:
				if(tablero[x][y].getBackground() == Color.BLACK) {
					tren[trenNum].setLongitud(--longitud);
					if(tren[trenNum].getLongitud() == 0)
						tren[trenNum].setCabeza(false);
					else {
					this.newCabeza(trenNum, x, --y);
					this.movTren(trenNum);
					}
				}else {
					for(int j =0 ; j<longitud; j++) 
						mover(x, y-j);
					
					if(y+1 < fila && tablero[x][y+1].getBackground() != Color.BLACK) {
						this.newCabeza(trenNum, x, y+1);
					}else {
						tren[trenNum].setLongitud(--longitud);
						if(tren[trenNum].getLongitud() == 0)
							tren[trenNum].setCabeza(false);
					}
				}

				break;
			case 2:
				if(tablero[x][y].getBackground() == Color.BLACK) {
					tren[trenNum].setLongitud(--longitud);
					if(tren[trenNum].getLongitud() == 0)
						tren[trenNum].setCabeza(false);
					else {
						this.newCabeza(trenNum, ++x, y);
						this.movTren(trenNum);
					}
				}else {
					for(int j =0 ; j<longitud; j++) 
						mover(x+j, y);
					
					if(x-1 >= 0 && tablero[x-1][y].getBackground() != Color.BLACK) {
						this.newCabeza(trenNum, x-1, y);
					}else {
						tren[trenNum].setLongitud(--longitud);
						if(tren[trenNum].getLongitud() == 0)
							tren[trenNum].setCabeza(false);
					}
				}

				break;
				
			case 3:
				if(tablero[x][y].getBackground() == Color.BLACK) {
					tren[trenNum].setLongitud(--longitud);
					if(tren[trenNum].getLongitud() == 0)
						tren[trenNum].setCabeza(false);
					else {
						this.newCabeza(trenNum, --x, y);
						this.movTren(trenNum);
					}
				}else {
				
					for(int j =0 ; j<longitud; j++) {
						mover(x-j, y);
					}
						
					if(x+1 < col && tablero[x+1][y].getBackground() != Color.BLACK) {
						this.newCabeza(trenNum, x+1, y);
					}else {
						tren[trenNum].setLongitud(--longitud);
						if(tren[trenNum].getLongitud() == 0)
							tren[trenNum].setCabeza(false);
					}
				}

				break;
			}
		}
	}

	public void movTrenes() {
		
		this.ordenaTrenes();
		for(int i=0; i<trenNum; i++) {
			this.movTren(i);
		}
	}
	
	private Casilla buscaCabeza(int x, int y) {
		//0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
		if(!tablero[x][y].isCabeza()){
			int sentido = -1;
			if(tablero[x][y].getBackground() == Color.BLUE)
				sentido = 0;
			else if(tablero[x][y].getBackground() == Color.GREEN)
				sentido = 1;
			else if(tablero[x][y].getBackground() == Color.RED)
				sentido = 2;
			else if(tablero[x][y].getBackground() == Color.YELLOW)
				sentido = 3;
			
			switch(sentido) {
			case 0:
				return buscaCabeza(x,--y);		
			case 1:
				return buscaCabeza(x,++y);
			case 2:
				return buscaCabeza(--x,y);
			default:
				return buscaCabeza(++x,y);
			}
		}
		else {
			return tablero[x][y];
		}
		
	}
	
	public void newCabeza(int trenNum, int newX, int newY) {		

		int ID = tren[trenNum].getTrenID();
		int sentido = tren[trenNum].getSentido();
		int longitud = tren[trenNum].getLongitud();
		tren[trenNum].setLongitud(0);
		tren[trenNum].setSentido(-1);
		tren[trenNum].setTrenID(0);
		tren[trenNum].setCabeza(false);

		tren[trenNum] = tablero[newX][newY];
		tren[trenNum].setSentido(sentido);
		tren[trenNum].setLongitud(longitud);
		tren[trenNum].setTrenID(ID);
		tren[trenNum].setCabeza(true);
	
	}
	
	private boolean inTable(int coorX, int coorY) {
		boolean salida = false;
		
		if(coorX >=0 && coorX <col && coorY >=0 && coorY < fila)
			salida = true;
		
		return salida;
	}

}
