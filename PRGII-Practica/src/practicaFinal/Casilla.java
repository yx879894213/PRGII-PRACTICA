package practicaFinal;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Casilla extends JButton {
	
	private boolean cabeza;
	private int ID;
	private int sentido;
	private int longitud;
	private int coorX;
	private int coorY;
	
	public Casilla(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public Casilla(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public Casilla(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}
	
	public Casilla(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}
	
	public Casilla() {
		super();
		this.cabeza = false;
		this.ID = -1;
	}
	public Casilla(int coorX, int coorY) {
		super();
		this.cabeza = false;
		this.ID = -1;
		this.coorX = coorX;
		this.coorY = coorY;
	}
		
	/**
	 * 
	 * @param sentido
	 * 0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
	 */
	public void setSentido(String sentido) {
			
		switch (sentido.toUpperCase()) {
			case "B":
				this.sentido = 0;			break;
			case "A":
				this.sentido = 1;			break;
			case "I":
				this.sentido = 2;			break;
			case "D":
				this.sentido = 3;			break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + sentido);
		}
	}
	
	public void setSentido(int sentido) {
		this.sentido = sentido;
	}
		
	public int getSentido() {
		return sentido;
	}
		
	public int getLongitud() {
		return longitud;
	}
	public void setLongitud(int l) {
		if(l>=0)
			this.longitud = l;
		else
			this.cabeza= false;
			
	}
	
	public int getCoorX() {
		return coorX;
	}
		
	public int getCoorY() {
		return coorY;
	}
	
	/**
	 * @return the cabeza
	 */
	public boolean isCabeza() {
		return cabeza;
	}

	/**
	 * @param cabeza the cabeza to set
	 */
	public void setCabeza(boolean cabeza) {
		this.cabeza = cabeza;
	}
	
	public int getTrenID(){
		return this.ID;
	}
	
	public void setTrenID(int trenNum) {
		this.ID = trenNum;
	}


}