package Minas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import Utiles.Semaforo;

public class AplicacionMinas extends JFrame {

	private static final String[] CONTINENTES = { "Europa", "Asia" };
	private static final String[] TIPOS = { "Regular", "Buena", "Excelente" };
	
	private static int totalToneladas;
	private static int[] material;
	private static int[] materialMax;
	private static int[] matVendidoContinente;
	private static int[] matVendidoTipo;
	
	private Pais[] paises;
	private JTable[] tablas;
	
	public AplicacionMinas() {
		material = new int[3];
		materialMax = new int[2];
		matVendidoContinente = new int[2];
		matVendidoTipo = new int[3];

		material[0] = (int)(totalToneladas*0.30);
		material[1] = material[0]*2;
		material[2] = totalToneladas - (material[0]+material[1]);
		
		materialMax[0] = materialMax[1] = totalToneladas/2;
		if((totalToneladas%2) != 0) 
			materialMax[0]++;
		
		tablas = new JTable[3];
		Pais.setReferencias(tablas, material, matVendidoContinente, matVendidoTipo, materialMax, new Semaforo(1));
		System.out.println("Toneladas totales: " +totalToneladas);
		System.out.println("Toneladas por Europa: " +materialMax[0] + " | Asia: " +materialMax[1]);
		System.out.println("Regulares: " +material[0] +" | Buenas: " +material[1] + " | Excelentes: " +material[2]);
		
		hazInterfaz();
	}
	
	public void hazInterfaz() {
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		JPanel panelPeticiones = new JPanel(new GridLayout(1, 0));
		
		Random random = new Random();
		int[] nPaises = new int[2];
		nPaises[0] = 10 + random.nextInt(21);
		nPaises[1] = 5 + random.nextInt(3);
		paises = new Pais[nPaises[0]+nPaises[1]];
		
		JPanel[] panels = new JPanel[nPaises.length+1];
		final String[] colNombres = { "Pais/Tipo", "Regular", "Buena", "Excelente" };
		Object[][] datos = null;
		for(int i = 0; i < nPaises.length; i++) {
			datos = new Object[nPaises[i]][4];
			for(int j = 0; j < nPaises[i]; j++)
				datos[j][0] = CONTINENTES[i] + (j+1);

			tablas[i] = new JTable(datos, colNombres);
			panels[i] = new JPanel();
			panels[i].setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), CONTINENTES[i], TitledBorder.CENTER, TitledBorder.TOP));
			//panels[i].add(new JScrollPane(tablas[i]));
			panels[i].add(tablas[i].getTableHeader());
			panels[i].add(tablas[i]);
			panelPeticiones.add(panels[i]);
		}
		
		Object[][] totales = new Object[1][6];
		totales[0][0] = totalToneladas;
		final String[] colTNombres = { "Toneladas", "Vendido Europa", "Vendido Asia", "Regular", "Buena", "Excelente" };
		panels[nPaises.length] = new JPanel(new GridLayout(0, 1));
		panels[nPaises.length].setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Totales", TitledBorder.CENTER, TitledBorder.TOP));
		tablas[nPaises.length] = new JTable(totales, colTNombres);
		panels[nPaises.length].add(tablas[nPaises.length].getTableHeader());
		panels[nPaises.length].add(tablas[nPaises.length]);
		add(panelPeticiones);
		add(panels[nPaises.length], BorderLayout.SOUTH);
		
		for(int i = 0; i < paises.length; i++) {
			if(i < nPaises[0])
				paises[i] = new Pais(CONTINENTES[0] + (i+1), 0, i);
			else
				paises[i] = new Pais(CONTINENTES[1] + (i+1-nPaises[0]), 1, i-nPaises[0]);
		}
		
		for(int i = 0; i < paises.length; i++) 
			paises[i].start();

		//setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		while(hayaVivos());
		
		tablas[2].setValueAt(matVendidoContinente[0], 0, 1);
		tablas[2].setValueAt(matVendidoContinente[1], 0, 2);
		tablas[2].setValueAt(matVendidoTipo[0], 0, 3);
		tablas[2].setValueAt(matVendidoTipo[1], 0, 4);
		tablas[2].setValueAt(matVendidoTipo[2], 0, 5);
	}
	
	public boolean hayaVivos() {
		//System.out.print("");
		for(int i = 0; i < paises.length; i++) {
			if(paises[i].isAlive())
				return true;
		}
		
		return false;
	}
	
	public static int optionPane_leerEntero(String pregunta, int li, int ls) {
		String szn;
		int n;
		try {
			szn = JOptionPane.showInputDialog(null, pregunta +" (" +li +"-" +ls +")");
			if(szn == null) 
				return li-1;
			
			n = Integer.parseInt(szn);
			if(n < li || n > ls) 
				return optionPane_leerEntero(pregunta, li, ls);
			
			return n;
		}
			
		catch(Exception e) {
			return optionPane_leerEntero(pregunta, li, ls);
		}
	}
	
	public static void main(String[] args) {
		final int MIN = 10000;
		final int MAX = 20000;
		
		totalToneladas = optionPane_leerEntero("Toneladas: ", MIN, MAX);
		if(totalToneladas == MIN-1) 
			return;
		
		new AplicacionMinas();
	}
}
