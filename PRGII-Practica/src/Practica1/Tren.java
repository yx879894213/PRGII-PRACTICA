package Practica1; 

public class Tren {

	private int sentido;
	private int longitud;
	private int coorX;
	protected int coorY;
	
	public Tren(String sentido, int longitud, int coordinadorX,int coordinadorY) {
		
		setSentido(sentido);
		this.longitud = longitud;
		this.coorX = coordinadorX;
		this.coorY = coordinadorY;
	}
	
	/**
	 * 
	 * @param sentido
	 * 0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
	 */
	private void setSentido(String sentido) {
		
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
	
	public int getSentido() {
		return sentido;
	}
	
	public int getLongitud() {
		return longitud;
	}
	public void setLongitud(int l) {
		this.longitud = l;
	}
	
	public int getCoorX() {
		return coorX;
	}
	
	public void setCoorX(int x) {
		this.coorX = x;
	}
	
	public int getCoorY() {
		return coorY;
	}
	
	public void setCoorY(int y) {
		this.coorY = y;
	}

}
