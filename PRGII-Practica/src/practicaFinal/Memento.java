package practicaFinal;

import javax.swing.JFrame;

public class Memento {
	   private TableroImplement state;

	   public Memento(TableroImplement state){
	      this.state = state;
	   }

	   public TableroImplement getState(){
	      return state;
	   }	
}