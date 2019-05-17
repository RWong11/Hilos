package Minas;

import java.util.Random;

import javax.swing.JTable;

import Utiles.Semaforo;

public class Pais extends Thread {
	private String nombre;
	private int continente;
	private int indice;
	private int[] toneladas;
	private int peticiones;
	
	private static Semaforo semaforo;
	private static JTable[] tablas;
	private static int materialMax[];
	private static int[] material;
	private static int[] matVendidoContinente;
	private static int[] matVendidoTipo;
	
	public Pais(String nombre, int continente, int indice) {
		this.nombre = nombre;
		this.continente = continente;
		this.indice = indice;
		toneladas = new int[3];
	}
	
	public static void setReferencias(JTable[] tablas, int[] material, int[] matVendidoContinente, int[] matVendidoTipo, int materialMax[], Semaforo semaforo) {
		Pais.tablas = tablas;
		Pais.material = material;
		Pais.matVendidoContinente = matVendidoContinente;
		Pais.matVendidoTipo = matVendidoTipo;
		Pais.materialMax = materialMax;
		Pais.semaforo = semaforo;
	}
	
	public void run() {
		comprarPlata();
	}
	
	public void comprarPlata() {
		while(true) {
			Random random = new Random();
			int tipo = random.nextInt(3);
			int cantidad = 1 + random.nextInt(3);

			semaforo.Espera();
			if(matVendidoContinente[continente] == materialMax[continente]) {
				semaforo.Libera();
				break;
			}
			
			if((matVendidoContinente[continente]+cantidad) > materialMax[continente])
				cantidad = materialMax[continente] - matVendidoContinente[continente];
				
			if((matVendidoTipo[tipo]+cantidad) > material[tipo]) {
				semaforo.Libera();
				continue;
			}
			
			matVendidoContinente[continente] += cantidad;
			matVendidoTipo[tipo] += cantidad;
			semaforo.Libera();
			tablas[2].setValueAt(matVendidoContinente[continente], 0, 1+continente);
			tablas[2].setValueAt(matVendidoTipo[tipo], 0, 3+tipo);
			peticiones++;
			tablas[continente].setValueAt("<html> <b>" +toneladas[tipo] +"<font color='green'> (+" +cantidad +")", indice, tipo+1);
			toneladas[tipo] += cantidad;
			dormir(50);
			tablas[continente].setValueAt(toneladas[tipo], indice, tipo+1);
		}	
	}
	
	public void dormir(int time) {
		try {
			sleep(time);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
