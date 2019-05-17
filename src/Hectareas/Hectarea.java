package Hectareas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Hectarea extends JLabel {
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
		super("", JLabel.CENTER);
		this.tipo = tipo;
//		ponImagen();
	}
	
	public String nomImg() {
		if(tipo == Tipo.MALA)
			return "imagenes/Mala.png";
		else if(tipo == Tipo.BUENA)
			return"imagenes/Buena.png";
		else
			return "imagenes/Excelente.png";
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
