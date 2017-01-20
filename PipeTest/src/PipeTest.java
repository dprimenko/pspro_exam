import java.io.IOException;

public class PipeTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		java.lang.Runtime rt = java.lang.Runtime.getRuntime();
        // Start two processes: ps ax | grep rbe
        java.lang.Process p1 = rt.exec("ps ax");
        // grep will wait for input on stdin
        java.lang.Process p2 = rt.exec("grep rbe");
        // Create and start Piper
        Piper pipe = new Piper(p1.getInputStream(), p2.getOutputStream());
        new Thread(pipe).start();
        // Wait for second process to finish
        p2.waitFor();
        // Show output of second process
        java.io.BufferedReader r = new java.io.BufferedReader(new java.io.InputStreamReader(p2.getInputStream()));
        String s = null;
        while ((s = r.readLine()) != null) {
            System.out.println(s);
        }
	}

}
