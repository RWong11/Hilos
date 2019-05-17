package Hectareas;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import Utiles.SemaforoBandera;

public class Hermano extends Thread {

	int nHermano;
	private static Hectarea[] hectareas;
	private static SemaforoBandera[] sb;
	private static ArrayList<Hectarea> entregas;
	int totH;
	public static final Color[] coloresHermano = new Color[] { Color.BLACK, Color.BLUE, Color.DARK_GRAY };
	
	public Hermano(int nHermano) {
		this.nHermano = nHermano;
	}
	
	public static void setHectareas(Hectarea[] hectareas, SemaforoBandera[] sb, ArrayList<Hectarea> entregas) {
		Hermano.hectareas = hectareas;
		Hermano.sb = sb;
		Hermano.entregas = entregas;
	}
	
	public void run() {
		while(entregas.size() < hectareas.length) {
			int hectarea = ThreadLocalRandom.current().nextInt(hectareas.length);
			sb[hectarea].getSemaforo().Espera();
			if(sb[hectarea].getBandera()) {
				sb[hectarea].getSemaforo().Libera();
				continue;
			}
			
			sb[hectarea].setBandera(true);
			sb[hectarea].getSemaforo().Libera();
			hectareas[hectarea].setHermano(nHermano);
			hectareas[hectarea].setForeground(coloresHermano[nHermano]);
			int inicio = nHermano+1;
			hectareas[hectarea].setText("<html><img src=\"file:imagenes\\" +inicio +".png\"" +" width=\"40\" height=\"40\">");
			totH++;
			entregas.add(hectareas[hectarea]);
		}
	}
	
	public boolean hayLibre() {
		for(int i = 0; i < hectareas.length; i++) {
			if(!sb[i].getBandera())
				return true;
		}
		
		return false;
	}
}