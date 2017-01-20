package dam.pspro;

public class Relevos {

	private boolean carreraComenzada;
	
	public Relevos() {
		carreraComenzada = false;
	}
	
	public synchronized void cogerRelevo(int hilo) {
		while(!carreraComenzada) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Soy el hilo numero " + hilo + " y estoy corriendo!");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("He terminado de correr!\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		notify();
	}
	
	public void comenzarCarrera() {
		this.carreraComenzada = true;
	}

	public static void main(String[] args) {
		Relevos relevo = new Relevos();
		Atleta[] atletas = new Atleta[4];
		
		for (int i = 0; i < atletas.length; i++) {
			atletas[i] = new Atleta(relevo, i);
			atletas[i].start();
		}
		
		relevo.comenzarCarrera();
		
		for (int i = 0; i < atletas.length; i++) {
			try {
				atletas[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
}

class Atleta extends Thread {
	
	private Relevos relevo;
	private int numero;
	
	public Atleta(Relevos relevo, int numero) {
		this.relevo = relevo;
		this.numero = numero;
	}
	
	@Override
	public void run() {
		relevo.cogerRelevo(numero);
	}
}
