package Hectareas;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import Utiles.Rutinas;
import Utiles.SemaforoBandera;

public class Hermano extends Thread {

	int nHermano;
	int totH;
	private static Hectarea[] hectareas;
	private static SemaforoBandera[] sb;
	private static int TAM = 50;
	public static final Color[] coloresHermano = new Color[] { Color.BLUE, Color.RED, new Color(8,128,11) };
	public static final ImageIcon imgs[] = {Rutinas.AjustarImagen("imagenes/1.png", TAM, TAM),
			Rutinas.AjustarImagen("imagenes/2.png", TAM, TAM)
			,Rutinas.AjustarImagen("imagenes/3.png", TAM, TAM)};
	
	
	public Hermano(int nHermano) {
		this.nHermano = nHermano;
	}
	
	public static void setHectareas(Hectarea[] hectareas, SemaforoBandera[] sb) {
		Hermano.hectareas = hectareas;
		Hermano.sb = sb;

	}
	
	public void run() {
		while(hayLibre()) {
			int hectarea = ThreadLocalRandom.current().nextInt(hectareas.length);
			sb[hectarea].getSemaforo().Espera();
			if(sb[hectarea].getBandera()) {
				sb[hectarea].getSemaforo().Libera();
				continue;
			}
			
			sb[hectarea].setBandera(true);
			sb[hectarea].getSemaforo().Libera();
			hectareas[hectarea].setHermano(nHermano);
			hectareas[hectarea].setBorder(BorderFactory.createLineBorder(coloresHermano[nHermano], 2));
			hectareas[hectarea].setImagen(imgs[nHermano]);
			totH++;
			
			try {
				Thread.sleep(700);
			}catch(Exception e) {}
			
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