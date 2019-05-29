package Aviones;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;
import Utiles.Rutinas;
import Utiles.Semaforo;

public class Avion extends JComponent implements Runnable, ActionListener{
	
	private static final long serialVersionUID = 1L;
	TorreControl torre;
	int turno;
	private boolean usandoPista, aterrizo, sentido, puedeAterrizar, doWait;
	private Image avion= Rutinas.AjustarImagen("imagenes/Avion.png", 51, 36).getImage(); 
	private Image avionInv= Rutinas.AjustarImagen("imagenes/AvionRev.png", 51, 36).getImage();
	private Image avionG= Rutinas.AjustarImagen("imagenes/AvionRev.png", 105, 60).getImage();
	private static Semaforo s;
	int AvionX,AvionY;
	int intentos, time;
	Timer t;
	float alpha;
	static final int px = 1;
	
	public Avion(int turno, Semaforo sf, TorreControl vista) {
		this.turno = turno;
		this.torre = vista; 
		sentido = true;
		usandoPista = false;
		aterrizo = false;
		doWait = false;
		AvionX = Rutinas.nextInt(10, 650);
		AvionY = 190 - (turno * 35);
		time = torre.time;
		alpha = 1F;
		setBounds(AvionX, AvionY, 51, 36);
		s = sf; 
		t = new Timer(time, this);
		System.out.println("Avion " + turno);
	}
	
	
	
	public void run() {
		t.start();
		
		while(!doWait) {
			System.out.print("");
		}
		
		s.Espera();
		sentido = true;
		puedeAterrizar = true;
		torre.turno++;
	}
	
	public void paint(Graphics g) {		
		super.paint(g);
		if(alpha > 0) 
			dibujarAvion((Graphics2D) g.create());
	}
	
	public void dibujarAvion(Graphics2D g) {
		setLocation(AvionX, AvionY);
		g.setComposite(AlphaComposite.SrcOver.derive(alpha));

		if(sentido)
			g.drawImage(avion, 0, 10, 50,25,null);
		else {
			if(!usandoPista)
				g.drawImage(avionInv, 0, 10, 50, 25, null);
			else 
				g.drawImage(avionG, 0, 10, 90,60,null);
		}
		
		g.drawString("#" +(turno+1), 10, 10);
	}

	public void volarEspera() {
			if(AvionX <= 10) 
				sentido = true;
			if(AvionX > 650) {
				sentido = false;
				if(puedeAterrizar) {
					usandoPista = true;
					setBounds(AvionX, AvionY, 125, 70);
				}
				
				else if(turno == torre.turno) 
					doWait = true;
				else 
					intentos++;
			}			

			if(sentido)
				AvionX += px;
			else
				AvionX -= px;
	}

	
	public void aterrizar() {

		if(aterrizo) {
			if(alpha > 0) {
				alpha -= 0.01F;
			}
			else {
				s.Libera();
				t.stop();
				System.out.println("El avión con el turno #" +(turno+1) + " intentó: " + intentos);
			}
			
			return;
		}
		
		if(AvionY == 310) {
			if(AvionX > 50)
				AvionX -= px*2;
	
			else {
				time = 5;
				aterrizo = true;
			}
		}
		else {
			AvionY += px;
			AvionX -= px;
		}
		
		if(AvionX % 12 == 0) {
			time++;
			t.setDelay(time);
		}
			
	}
	
	public void actionPerformed(ActionEvent evt) {
		if(usandoPista)
			aterrizar();
		else 
			volarEspera();

		repaint();
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public void start() {
		new Thread(this).start();
	}

	public boolean isAlive() {
		return new Thread(this).isAlive();
	}
}