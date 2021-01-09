package practicaFinal;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.*;

import javax.swing.Icon;
import javax.swing.JOptionPane;

public class Main {

	static TableroImplement tablero;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
					tablero = new TableroImplement(fila, col);
					tablero.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Conjunto incorrecto","ERROR", JOptionPane.ERROR_MESSAGE, null );
				}
			}
		});

	}

}
