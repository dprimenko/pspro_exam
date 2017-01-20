import java.nio.IntBuffer;

class Escritor extends Thread {
	
	private Check check;
	
	public Escritor(Check check) {
		this.check = check;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			check.escribirEnBuffer();
		}
	}
}

class Lector extends Thread {
	
	private Check check;
	
	public Lector(Check check) {
		this.check = check;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			check.leerBuffer();
		}
	}
}


public class Check {

	private IntBuffer buffer;
	private volatile int count;
	private boolean haEscrito;
	
	public IntBuffer getBuffer() {
		return buffer;
	}
	
	public Check() {
		buffer = IntBuffer.allocate(10000);
		count = 0;
		haEscrito = false;
	}
	
	
	public synchronized void leerBuffer() {
		
		while (!haEscrito) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		boolean error = false;
		
		for (int i = 0; i < buffer.capacity(); i++) {		
			if (count != buffer.get(i)) {
				error = true;
			}
		}
		
		if(error) {
			System.out.println("Incorrecto!");
		} else {
			System.out.println("Correcto!");
		}
		haEscrito = false;
		count++;
		notify();
	}
	
	public synchronized void escribirEnBuffer() {
		
		while (haEscrito) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put(i, count);
		}
		System.out.println("Pasada: " + count);
		haEscrito = true;
		notify();
	}
	
	public static void main(String[] args) {
		Check check = new Check();
		
		Lector lector = new Lector(check);
		Escritor escritor = new Escritor(check);
		
		lector.start();
		escritor.start();
		
		try {
			lector.join();
			escritor.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
