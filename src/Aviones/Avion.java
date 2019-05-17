package Aviones;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

import Utiles.Rutinas;
import Utiles.Semaforo;

import java.util.concurrent.Semaphore;

public class Avion extends JComponent implements Runnable, ActionListener{
	
	TorreControl torre;
	int turno;
	private boolean usandoPista, aterrizo, sentido, puedeAterrizar, doWait;
//	private Image avion= Rutinas.AjustarImagen("imagenes/Avion.png", 51, 36).getImage(); 
//	private Image avionInv= Rutinas.AjustarImagen("imagenes/AvionRev.png", 51, 36).getImage();
//	private Image avionG= Rutinas.AjustarImagen("imagenes/AvionRev.png", 95, 60).getImage();
	private static final Image avion = new ImageIcon("imagenes/Avion.png").getImage();
	private Semaforo s;
	int AvionX,AvionY;
	int intentos;
	Timer t;
	int time;
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

		setBounds(AvionX, AvionY, 51, 36);
		this.s = sf; 
		time = torre.time;
		t = new Timer(time, this);
		System.out.println("Avion " + turno);
		alpha = 1F;
	}
	
	public float getAlpha() {
		return alpha;
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
		AffineTransform identity = new AffineTransform();
		AffineTransform trans = new AffineTransform();
		trans.setTransform(identity);
		trans.rotate( Math.toRadians(sentido ? 0 : 180) );
		g.drawImage(avion, trans, this);
//		if(sentido)
//			g.drawImage(avion, 0, 10, 50,25,null);
//		else {
//			if(!usandoPista)
//				g.drawImage(avionInv, 0, 10, 50, 25, null);
//			else 
//				g.drawImage(avionG, 0, 10, 90,60,null);
//		}
		
		g.drawString("#" +(turno+1), 10, 10);
	}

	public void volarEspera() {
			if(AvionX <= 10) 
				sentido = true;
			if(AvionX > 650) {
				sentido = false;
				if(puedeAterrizar) {
					usandoPista = true;
					setBounds(AvionX, AvionY, 95, 70);
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
//		if(!usandoPista) {
//			AvionX = 560;
//			//AvionY = 330;
//			sentido = false;
//			usandoPista = true;
//			setBounds(AvionX, AvionY, 95, 70);
//		}

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
	
	public void start() {
		new Thread(this).start();
	}

	public boolean isAlive() {
		return new Thread(this).isAlive();
	}
}