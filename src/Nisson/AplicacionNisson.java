package Nisson;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Utiles.Semaforo;

public class AplicacionNisson extends JFrame {
	public static final String[] ESTACIONES = { "Chasis y cableado", "Motor-transmisión", "Carrocería", "Interiores", "Llantas", "Pruebas" };
	public static final int[] DURACION = new int[] { 20, 6, 10, 5, 5, 10 }; 
	public static final int[] ROBOTS = new int[] { 5, 4, 3, 3, 0, 0 }; 
	public static final int ROB_TRANSMISION = 2;
	public static boolean resize;
	
	private Estancia[][] estancias;
	private Linea[] lineas;
	private Semaforo[] semaforos;
	private static int nLineas;
	private static int maxCarros;
	
	public static void main(String[] args) {
		nLineas = optionPane_leerEntero("Numero de lineas: ", 8, 15);
		if(nLineas == 7) 
			return;
		
		maxCarros = optionPane_leerEntero("Carros a fabricar: ", 1, 9999999);
		if(maxCarros == 0) 
			return;

		resize = (nLineas >= 12);
		new AplicacionNisson();
	}

	public AplicacionNisson() {
		setSize(900, 600);
		
		JPanel panelEstaciones = new JPanel(new GridLayout(0, 6));
		add(panelEstaciones);
		
		JLabel txtEstacion;
		for(int i = 0; i < 6; i++) {
			panelEstaciones.add(txtEstacion = new JLabel(ESTACIONES[i], JLabel.CENTER));
			txtEstacion.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		}
		
		
		estancias = new Estancia[nLineas][6];
		for(int i = 0; i < nLineas; i++) {
			for(int j = 0; j < 6; j++)
				panelEstaciones.add(estancias[i][j] = new Estancia(DURACION[j]));
		}

		semaforos = new Semaforo[7];
		Linea.setSemaforos(semaforos);
		Linea.setMaxCarros(maxCarros);
		semaforos[6] = new Semaforo(ROB_TRANSMISION);
		for(int i = 0; i < 6; i++)
			semaforos[i] = new Semaforo(ROBOTS[i] == 0 ? nLineas : ROBOTS[i]);

		lineas = new Linea[nLineas];
		for(int i = 0; i < nLineas; i++) 
			lineas[i] = new Linea(estancias[i]);
		
		for(int i = 0; i < nLineas; i++) 
			lineas[i].start();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		long tinicio = System.currentTimeMillis();
		while(hayaVivos());
		long tfinal = System.currentTimeMillis();
		
		JOptionPane.showMessageDialog(null, "<html>Se han fabricado <font color='red'>" +maxCarros +"</font> carros trabajando con <font color='red'>" +nLineas +"</font> lineas en <font color='red'>" +((tfinal-tinicio)/1000L) +"</font> segundos.");
		System.exit(0);
	}
	
	public boolean hayaVivos() {
		for(int i = 0; i < nLineas; i++) {
			if(lineas[i].isAlive())
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
}