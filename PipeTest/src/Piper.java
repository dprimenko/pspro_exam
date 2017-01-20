
public class Piper implements Runnable {

	private java.io.InputStream input;

    private java.io.OutputStream output;

    public Piper(java.io.InputStream input, java.io.OutputStream output) {
        this.input = input;
        this.output = output;
    }

	
	@Override
	public void run() {
		try {
            // Create 512 <a class="zem_slink" href="http://en.wikipedia.org/wiki/Byte" title="Byte" rel="wikipedia" target="_blank">bytes</a> buffer
            byte[] b = new byte[512];
            int read = 1;
            // As long as data is read; -1 means <a class="zem_slink" href="http://en.wikipedia.org/wiki/End-of-file" title="End-of-file" rel="wikipedia" target="_blank">EOF</a>
            while (read != -1) {
                // <a class="zem_slink" href="http://en.wikipedia.org/wiki/Read_%28system_call%29" title="Read (system call)" rel="wikipedia" target="_blank">Read</a> bytes into buffer
                read = input.read(b, 0, b.length);
                //System.out.println("read: " + new String(b));
                if (read != -1) {
                    // Write bytes to output
                    output.write(b, 0, read);
                }
            }
        } catch (Exception e) {
            // Something happened while reading or writing streams; pipe is broken
            throw new RuntimeException("Broken pipe", e);
        } finally {
            try {
                input.close();
            } catch (Exception e) {
            }
            try {
                output.close();
            } catch (Exception e) {
            }
        }

	}

}
