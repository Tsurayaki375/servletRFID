package secuRIFD;

import java.io.IOException;

public class App {

	public static void main(String[] args) throws IOException {
		Detection detection = new Detection(0);
		detection.run();
	}
}