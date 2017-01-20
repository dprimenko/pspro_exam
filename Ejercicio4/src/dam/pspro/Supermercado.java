package dam.pspro;

import java.util.Random;

class Cliente extends Thread {
	
	private Supermercado supermercado;
	private int numCliente;
	
	public Cliente(Supermercado supermercado, int numCliente) {
		this.supermercado = supermercado;
		this.numCliente = numCliente;
	}
	
	@Override
	public void run() {
		
		try {
			Thread.sleep(new Random().nextInt(5000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int caja = new Random().nextInt(supermercado.getNumCajas());
		System.out.println("Intentando acceder a la caja " + caja);
		supermercado.pasarPorCaja(caja, numCliente);
	}
}

public class Supermercado {

	
	private int[] cajas;
	private int cajaUsada;
	
	public int getNumCajas() {
		return cajas.length;
	}
	
	public Supermercado(int cajas) {
		this.cajas = new int[cajas];
	}
	
	public void pasarPorCaja(int caja, int cliente) {
		cajas[caja] = new Random().nextInt(1000);
		
		synchronized(new Object()) {
			System.out.println("El cliente " + cliente + " está pagando en la caja " + caja);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cajaUsada = cajas[caja];
			System.out.println("El cliente " + cliente + " ha pagado " + cajaUsada + "€\n");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Supermercado supermercado = new Supermercado(Integer.parseInt(args[0]));
		Cliente[] clientes = new Cliente[Integer.parseInt(args[1])];
		
		for(int i = 0; i < clientes.length; i++) {
			clientes[i] = new Cliente(supermercado, i);
			clientes[i].start();
			try {
				clientes[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
