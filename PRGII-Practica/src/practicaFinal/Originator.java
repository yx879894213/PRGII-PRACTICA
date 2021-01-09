package practicaFinal;

import javax.swing.JFrame;

public class Originator {
	   private TableroImplement state;

	   public void setState(TableroImplement state){
	      this.state = state;
	   }

	   public TableroImplement getState(){
	      return state;
	   }

	   public Memento guardar(){
	      return new Memento(state);
	   }

	   public void restaurar(Memento memento){
	      this.state = memento.getState();
	   }
}