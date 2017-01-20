package dam.pspro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProcesosConectados {
	
	public static void main(String[] args) {
		
		Process p1 = null;
		Process p2 = null;
		
		try {
			p1 = new ProcessBuilder("dpkg", "--get-selections").start(); 
			p2 = new ProcessBuilder("grep", "figlet").start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader brA = null;
		BufferedWriter bw = null;
		
		brA = new BufferedReader(new InputStreamReader(p1.getInputStream()));
		bw = new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()));
		
		String lineA = "";
		try {
			while ((lineA = brA.readLine()) != null) {
				bw.write(lineA);
				bw.newLine();
			}
			brA.close();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		BufferedReader brB = null;
		String lineB = "";
		
		brB = new BufferedReader(new InputStreamReader(p2.getInputStream()));
		
		try {
			while ((lineB = brB.readLine()) != null) {
				System.out.println(lineB);
			}
			brB.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			p1.waitFor();
			p2.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
