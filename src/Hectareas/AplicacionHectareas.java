package Hectareas;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import Hectareas.Hectarea.Tipo;
import Utiles.SemaforoBandera;

public class AplicacionHectareas extends JFrame {
	
	private static final long serialVersionUID = 1L;
	Hectarea[] hectareas;
	Hermano[] hermanos;
	ArrayList<Hectarea> entregas;
	
	public static void main(String[] args) {
		new AplicacionHectareas();
	}
	
	public AplicacionHectareas() {
		setSize(800, 800);
		setLayout(new GridLayout(10, 10));
		
		hectareas = new Hectarea[100];
		Tipo t;
		
		for(int i = 0; i < hectareas.length; i++) {
			t = Tipo.values()[new Random().nextInt(Tipo.values().length)];
			hectareas[i] = new Hectarea(t);
			hectareas[i].setBorder(BorderFactory.createLineBorder(t.getColor(), 2));
			add(hectareas[i]);
		}
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		entregas = new ArrayList<Hectarea>();
		SemaforoBandera[] sb = new SemaforoBandera[hectareas.length];
		for(int i = 0; i < sb.length; i++) 
			sb[i] = new SemaforoBandera();
		
		hermanos = new Hermano[3];
		Hermano.setHectareas(hectareas, sb, entregas);

		for(int i = 0; i < hermanos.length; i++) 
			hermanos[i] = new Hermano(i);

		for(int i = 0; i < hermanos.length; i++) 
			hermanos[i].start();
		
		while(hayaVivos());
		
		for(Hermano herm: hermanos )
			System.out.println("Total de hectareas hermano " + (herm.nHermano+1) + ": " + herm.totH);
		
//		int inicio;
//		for(Hectarea h: entregas) {
//			h.setForeground(coloresHermano[h.getHermano()]);
//			inicio = h.getHermano()+1;
//			h.setText("<html><img src=\"file:imagenes\\hectarea\\" +inicio +".png\"" +" width=\"60\" height=\"60\">");
//			try {
//				Thread.sleep(50);
//			} catch (Exception e) { }
//		}
	}
	
	public boolean hayaVivos() {
		for(int i = 0; i < hermanos.length; i++) {
			if(hermanos[i].isAlive())
				return true;
		}
		
		return false;
	}
}
