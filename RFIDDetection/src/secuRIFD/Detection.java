package secuRIFD;

import javax.swing.*;
import javax.smartcardio.CardException;
import java.io.IOException;
import java.net.URLEncoder;

public class Detection {
	private ScanUID myScanUID = new ScanUID();
	private int id = 0;

	public Detection(int id) throws IOException {
		this.id = id;
	}

	/**
	 * Function to create a frame who displays a web page (in this case
	 * a servlet response)
	 */
	public JFrame doBrowserFrame(String url) throws IOException {
		JEditorPane website = new JEditorPane(url);
		JFrame frame = new JFrame("Browser RFID Detection");
		frame.setSize(600, 280);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		JScrollPane jScrollPane = new JScrollPane(website);
		frame.add(jScrollPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}

	/**
	 * Function to display a frame on the monitor in real time. If no card is
	 * present, this is a "basic frame" who displays informations. Else, an other
	 * frame appears and displays informations of the employee (firstname, lastname,
	 * post, his security level and weither the access is granted or not).
	 */
	public void run() throws IOException {
		JFrame mainFrame = doBrowserFrame("http://localhost:50001/webProject/RFIDAuthorization");
		SendUserPassage sendUserPassage = new SendUserPassage();

		String UID = "";
		String terminalID = "";

		while (true) {
			if (myScanUID.isCardHere()) {
				myScanUID.openConnection(id);
				try {
					UID = myScanUID.getUidCard();
					terminalID = myScanUID.getTerminalID(id);
					mainFrame.setVisible(false);
					JFrame cardFrame = doBrowserFrame("http://localhost:50001/webProject/RFIDAuthorization?idCard="
							+ UID + "&idTerminal=" + URLEncoder.encode(terminalID, "UTF-8"));
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sendUserPassage.run(UID, terminalID);
					cardFrame.setVisible(false);
					cardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				} catch (CardException e1) {
					e1.printStackTrace();
				}

			} else {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mainFrame.setVisible(true);
			}
		}
	}
}