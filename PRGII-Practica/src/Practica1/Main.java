package Practica1;

import java.util.Scanner;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);		

		do {
			try{
				
				int numTren = sc.nextInt();
				
				if(numTren <1 || numTren > Tablero.NUMTRENMAX)
					throw new IllegalArgumentException();
				
				Tablero juego = new Tablero(numTren);
				
				for(int i=0; i<numTren; i++)
					juego.AddTren(sc.next(), sc.nextInt(), sc.nextInt(), sc.nextInt());
				
				juego.play();
				System.out.println(juego.toString());
				
			}catch (Exception e) {
				System.out.println("Conjunto de trenes incorrecto");
			}
		}while(sc.hasNextInt());
		
		sc.close();
		
	}
	
}
