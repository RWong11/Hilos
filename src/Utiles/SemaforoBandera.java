package Utiles;

public class SemaforoBandera {
	private Semaforo semaforo;
	private boolean  bandera;
	public SemaforoBandera(){
		semaforo = new Semaforo(1);
		bandera = false;
	}
	
	public Semaforo getSemaforo() {
		return semaforo;
	}
	
	public boolean getBandera() {
		return bandera;
	}
	
	public void setBandera(boolean valor) {
		bandera = valor;
	}
}
