package Nisson;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Estancia extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public static final String ROBOT_ICON = "imagenes/robot.png";
	public static final String TRANSMISION_ICON = "imagenes/transmision.png";
	
	private JLabel robot;
	private JProgressBar progreso;
	private int segundos;
	
	public Estancia(int segundos) {
		setLayout(new BorderLayout());
		add(progreso = new JProgressBar(0, segundos), BorderLayout.NORTH);
		progreso.setStringPainted(true);
		progreso.setPreferredSize(new Dimension((int) progreso.getPreferredSize().getWidth(), 10));
		progreso.setString("");
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		robot = new JLabel("", JLabel.RIGHT);
		robot.setIcon(new ImageIcon((new ImageIcon(ROBOT_ICON).getImage()).getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
		add(robot, BorderLayout.EAST);
		robot.setVisible(false);
		this.segundos = segundos;
	}
	
	public void setCarro(JLabel carro) {
		add(carro);
		progreso.setBackground(Color.ORANGE);
		progreso.setString("Esperando Robot... ");
		updateUI();
	}
	
	public void showRobot(boolean activo) {
		robot.setVisible(activo);
		progreso.setString(activo ? "Fabricando" : "Listo");
		progreso.setBackground(Color.GREEN);
		updateUI();
	}
	
	public void reiniciarProgreso() {
		progreso.setValue(0);
		progreso.setString("");
		progreso.setBackground(Color.WHITE);
	}
	
	public boolean transmision() {
		return (progreso.getMaximum() != segundos);
	}
	
	public void cambiarTransmision(boolean activado) {
		progreso.setValue(0);
		
		if(activado) {
			robot.setIcon(new ImageIcon((new ImageIcon(TRANSMISION_ICON).getImage()).getScaledInstance(35, 30, java.awt.Image.SCALE_SMOOTH)));
			progreso.setMaximum(4);
		}
		else {
			robot.setIcon(new ImageIcon((new ImageIcon(ROBOT_ICON).getImage()).getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)));
			progreso.setMaximum(segundos);
		}
	}
	
	public boolean aumentar() {
		int p = progreso.getValue() + 1;
		progreso.setValue(p);
		
		return (p > progreso.getMaximum());
	}
}