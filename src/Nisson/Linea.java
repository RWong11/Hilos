package Nisson;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Utiles.Semaforo;

public class Linea extends Thread {
	
	private JLabel carro;
	private static int nCarros;
	private Estancia[] estancias;
	private static int maxCarros;
	private static Semaforo[] semaforos;
	
	public Linea(Estancia[] estancias) {
		carro = new JLabel("", JLabel.CENTER);
		this.estancias = estancias;
	}
	
	public static void setMaxCarros(int maxCarros) {
		Linea.maxCarros = maxCarros;
	}
	
	public static void setSemaforos(Semaforo[] semaforos) {
		Linea.semaforos = semaforos;
	}
	
	public void run() {
		fabricar();
	}
	
	public void fabricar() {
		int antEstacion = 0, sem;
		int num = aumentarCarro();
		while(num <= maxCarros) {
			carro.setText("#" +num);
			carro.setIcon(new ImageIcon((new ImageIcon("imagenes/carro" +num%3 +".png").getImage()).getScaledInstance(70, 40, java.awt.Image.SCALE_SMOOTH)));
			int estacion = 0;
			
			while(estacion < 6) {
				estancias[estacion].setCarro(carro);
				estancias[antEstacion].updateUI();
				
				sem = (estacion == 1 && estancias[estacion].transmision()) ? 6 : estacion;
				semaforos[sem].Espera();
				if(sem == 6)
					semaforos[1].Libera();

				estancias[estacion].showRobot(true);
				do {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) { }
				} while(!estancias[estacion].aumentar());
				
				antEstacion = estacion;
				estancias[estacion].showRobot(false);
				
				if(estacion == 1 && !estancias[estacion].transmision())
					estancias[estacion].cambiarTransmision(true);
				else {
					semaforos[sem].Libera();
					estacion++;
				}
			}

			System.out.println("Carro #" +num +" Listo" );
			for(int i = 0; i < 6; i++) 
				estancias[i].reiniciarProgreso();
			
			estancias[1].cambiarTransmision(false);
			num = aumentarCarro();
		}
		
		carro.setText("");
		carro.setIcon(null);
		estancias[antEstacion].updateUI();
	}
	
	public static synchronized int aumentarCarro() {
		return ++nCarros;
	}
}