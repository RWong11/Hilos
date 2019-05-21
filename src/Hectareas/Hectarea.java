package Hectareas;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Hectarea extends JPanel {

	
	private static final long serialVersionUID = 1L;

	enum Tipo {
		MALA(new Color(226,161,0)), 
		BUENA(new Color(219,226,0)), 
		EXCELENTE(new Color(0,226,110));
		
		private final Color color;
		
		Tipo(Color color) {
			this.color = color;
		}
		
		public Color getColor() {
			return color;
		}
	}

	private Tipo tipo;
	private int hermano;
	
	public Hectarea(Tipo tipo) {
		this.tipo = tipo;
		this.setBackground(tipo.getColor());
	}
	
	public void setImagen(ImageIcon img) {
		JLabel lbl = new JLabel(img);
		add(lbl);
		updateUI();
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public int getHermano() {
		return hermano;
	}
	
	public void setHermano(int hermano) {
		this.hermano = hermano;
	}
}
