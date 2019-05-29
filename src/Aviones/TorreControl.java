package Aviones;

import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Utiles.Rutinas;
import Utiles.Semaforo;

public class TorreControl extends JFrame{

	private static final long serialVersionUID = 1L;
	Avion aviones[];
	Semaforo S;
	int turno = 0;
	final int time = 5;
	
	public TorreControl() {
		S = new Semaforo(1);
		hacerAviones();
		hazInterfaz();	
		
		for(int i=0; i<aviones.length; i++) 
			aviones[i].start();
		
		System.out.println(turno);
		
		while(hayaVivos());	
	}
		
	public void hazInterfaz() {
		setSize(700,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setResizable(false);
		setVisible(true);
		
		setLayout(new BorderLayout());
        setContentPane(new JLabel(Rutinas.AjustarImagen("imagenes/Pista.png", 700, 400)));
        setLayout(null);
		for(int i=0; i<aviones.length; i++) 
			add(aviones[i]);
		
		setSize(699, 400);
		setSize(700, 425);
	}
	
	public void hacerAviones() {
		aviones = new Avion[5]; //Rutinas.nextInt(3, 6)
		Vector<Integer> turnos = new Vector<Integer>();
		for(int i=0;i<aviones.length;i++) 
			turnos.add(i);
		
		for(int i=0;i<aviones.length;i++) {
			int pos = Rutinas.nextInt(0, turnos.size()-1);
			int turno = turnos.get(pos);
			turnos.remove(pos);
			aviones[i] = new Avion(turno,S,this);
		}
	}
	
	public boolean hayaVivos() {
		System.out.print("");
		for(int i=0;i<aviones.length;i++) {
			if(aviones[i].getAlpha() > 0)
				return true;
		}
		return false;
	}
	
	public static void main(String args[]) {
		new TorreControl();
	}
	
}