package dam.pspro;

class Orden {
	
	private boolean segundoSaludo;
	
	public Orden() {
		segundoSaludo = false;
	}
	
	public synchronized void comprobarTurno(int numero) {
		while (!segundoSaludo) {
			if (numero != 2) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				notify();
				this.segundoSaludo = true;
			}
		}
	}
}

class Hilo extends Thread {
	
	private int numero;
	private Orden orden;
	
	public Hilo(int numero, Orden orden) {
		this.numero = numero;
		this.orden = orden;
	}
	
	@Override
	public void run() {
		orden.comprobarTurno(this.numero);
		System.out.println("Hola soy el hilo numero " + numero);
	}
}

class StartSaludos {
	public static void main(String[] args) {
		Orden orden = new Orden();
		
		Hilo hilo1 = new Hilo(1, orden);
		Hilo hilo2 = new Hilo(2, orden);
		Hilo hilo3 = new Hilo(1, orden);
		Hilo hilo4 = new Hilo(2, orden);
		
		hilo1.start();
		hilo2.start();
		
		try {
			hilo1.join();
			hilo2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
