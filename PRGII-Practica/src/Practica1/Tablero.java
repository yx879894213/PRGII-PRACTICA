package Practica1;

public class Tablero {
	
	public final static int LTOTALMAX = 100;
	public final static int LTRENMAX = 30;
	public final static int NUMTRENMAX = 10;
	private final int TAM = 30;
	private int trenNum = 0;
	private int lresto = 100;
	char[][] tablero = new char[30][30];
	Tren[] tren;
	
	public Tablero(int numTren) {
		
		tren = new Tren[numTren];
		inicializaTab();
	}
	
	public void inicializaTab() {
		
		for(int i=0; i<TAM; i++)
			for(int j=0; j<TAM; j++) {
				tablero[i][j] = '.';
			}
	}

	public void AddTren(String sentido, int longitud, int coordinadorX, int coordinadorY) {
		
		if(lresto-longitud < 0 || longitud > LTOTALMAX ) {
			throw new IllegalArgumentException();
		}else {
			tren[trenNum] = new Tren(sentido, longitud, coordinadorX, coordinadorY);
			this.rellenaTablero(tren[trenNum]);
			trenNum++;
			lresto-=longitud;
		}
	}
	
	public void rellenaTablero(Tren tren) {
		
		boolean entradaCorrecto = true;
		int sentido = tren.getSentido();
		int longitud = tren.getLongitud();
		int X = tren.getCoorX();
		int Y = tren.getCoorY();
		
		switch (sentido) {		//0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
		case 0:
			for(int i=0; i<longitud && entradaCorrecto == true; i++) {
				if(tablero[X][Y+i] == '.')
					tablero[X][Y+i] = '0';
				else
					entradaCorrecto = false;
			}
			break ;
		case 1:
			for(int i=0; i<longitud && entradaCorrecto == true; i++) {
				if(tablero[X][Y-i] == '.')
					tablero[X][Y-i] = '1';
				else
					entradaCorrecto = false;
			}
			break ;
		case 2:
			for(int i=0; i<longitud && entradaCorrecto == true; i++) {
				if(tablero[X+i][Y] == '.')
					tablero[X+i][Y] = '2';
				else
					entradaCorrecto = false;
			}
			break ;
		default:
			for(int i=0; i<longitud && entradaCorrecto == true; i++) {
				if(tablero[X-i][Y] == '.')
					tablero[X-i][Y] = '3';
				else
					entradaCorrecto = false;
			}
		}
		if(entradaCorrecto == false) {
			throw new IllegalArgumentException();
		}
	}
	
	private void ordenaTrenes() {
		
		Tren[] trenesOrdenado = new Tren[trenNum];
		int contador = 0;
		
		for(int j = 0; j< 4; j++) {
			for(int i = 0; i< trenNum; i++)
				if(tren[i].getSentido() == j)
					trenesOrdenado[contador++] = tren[i];
		}
		for(int i = 0; i < trenNum; i++)
			tren[i] = trenesOrdenado[i];	
	}
	
	public void play() {
		
		this.ordenaTrenes();
		while(!end())
			this.movTren();
	}
	
	private void mover(int x, int y) {
		
		//0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
		char elem = tablero[x][y];
		if(tablero[x][y] != 'X') {
			tablero[x][y] = '.';
		
			switch(elem) {
				case '0': 
					if(y-1 >=0) 
						if(tablero[x][y-1] == '.')
							tablero[x][y-1] = elem; 
						else if(tablero[x][y-1] != 'X')
							tablero[x][y-1] = 'X';
					break;
				case '1':
					if(y+1 < TAM) 
						if(tablero[x][y+1] == '.')
							tablero[x][y+1] = elem; 
						else if(tablero[x][y+1] != 'X')
							tablero[x][y+1] = 'X';
					break;
				case '2':
					if(x-1 >= 0) 
						if(tablero[x-1][y] == '.')
							tablero[x-1][y] = elem; 
						else if(tablero[x-1][y] != 'X')
							tablero[x-1][y] = 'X';
					break;
				case '3':
					if(x+1 < TAM) 
						if(tablero[x+1][y] == '.')
							tablero[x+1][y] = elem; 
						else if(tablero[x+1][y] != 'X')
							tablero[x+1][y] = 'X';
					break;
			}
		}
	}

	public void movTren() {
		
		for(int i=0; i<trenNum; i++) {
			int longitud = tren[i].getLongitud();
			
			int x = tren[i].getCoorX();
			int y = tren[i].getCoorY();
			int sentido = tren[i].getSentido();
			
			switch(sentido) {		//0-> Abajo, 1-> Arriba, 2-> Izquierda, 3-> Derecha.
			case 0:
				if(tablero[x][y] == 'X') {		//Si fuese chocado por otro tren.
					tren[i].setCoorY(++y);		//el coordenada Y se modifica por el anterior y
					tren[i].setLongitud(--longitud); //la longitud del tren se disminuye 1.
				}
				
				for(int j =0 ; j<longitud; j++) 
					mover(x, y+j);
				
				if(y-1 >= 0 && tablero[x][y-1] != 'X')
					tren[i].setCoorY(y-1);
				else 
					tren[i].setLongitud(--longitud);
				
				break;
			case 1:
				if(tablero[x][y] == 'X') {
					tren[i].setCoorY(--y);
					tren[i].setLongitud(--longitud);
				}
				
				for(int j =0 ; j<longitud; j++) 
					mover(x, y-j);
				
				if(y+1 < TAM && tablero[x][y+1] != 'X')
					tren[i].setCoorY(y+1);
				else 
					tren[i].setLongitud(--longitud);
				
				break;
			case 2:
				if(tablero[x][y] == 'X') {
					tren[i].setCoorX(++x);
					tren[i].setLongitud(--longitud);
				}
				for(int j =0 ; j<longitud; j++) 
					mover(x+j, y);
				
				if(x-1 >= 0 && tablero[x-1][y] != 'X')
					tren[i].setCoorX(x-1);
				else 
					tren[i].setLongitud(--longitud);
				
				break;
				
			case 3:
				if(tablero[x][y] == 'X') {
					tren[i].setCoorX(--x);
					tren[i].setLongitud(--longitud);
				}
				
				for(int j =0 ; j<longitud; j++) {
					mover(x-j, y);
				}
					
				if(x+1 < TAM && tablero[x+1][y] != 'X')
					tren[i].setCoorX(x+1);
				else 
					tren[i].setLongitud(--longitud);
				
				break;
			}
			
		}
	}
	
	private boolean end() {
		
		boolean salida = true;
		for(int i=0; i<TAM; i++)
			for(int j=0; j<TAM; j++) {
				if(tablero[i][j] != '.' && tablero[i][j]!= 'X')
					salida = false;
			}
		return salida;
	}

	public String toString() {
		
		int[] decenas = {0,1,2};
		int unidades = 10;
		int contadorDecena = 30;
		StringBuilder builder = new StringBuilder();
		
		builder.append("  ");
		for(int i=0; i<decenas.length; i++)
			for(int j=0; j<unidades;j++) {
				builder.append(" "+i);
			}
		
		builder.append("\n  ");
		for(int i=0; i<decenas.length; i++)
			for(int j=0; j<unidades;j++) {
				builder.append(" "+j);
			}
		
		for(int i=TAM-1; i>=0; i--) {
			builder.append("\n");
			builder.append(decenas[2]);
			contadorDecena--;
			
			if(contadorDecena %10 ==0)
				decenas[2]--;
			builder.append((contadorDecena)%10);
		
			for(int j=0; j<TAM; j++) {
				builder.append(" "+tablero[j][i]);
			}
		}
		
		builder.append("\n");

		return builder.toString();
	}
	
}
