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

	public void run() throws IOException {

		String mainUrl = "http://localhost:50001/webProject/RFIDAuthorization";
		JEditorPane mainWebsite = new JEditorPane(mainUrl);
		JFrame mainFrame = new JFrame("Browser RFID Detection");
		mainFrame.setSize(600, 300);
		mainFrame.setLocationRelativeTo(null);  
		mainFrame.setResizable(false);
		JScrollPane jScrollPane = new JScrollPane(mainWebsite);
		mainFrame.add(jScrollPane);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);

		String UID = "";
		String terminalID = "";

		while (true) {
			if (myScanUID.isCardHere()) {
				mainFrame.setVisible(false);
				myScanUID.openConnection(id);
				try {
					UID = myScanUID.getUidCard();
					terminalID = myScanUID.getTerminalID(id);

					String cardUrl = "http://localhost:50001/webProject/RFIDAuthorization?idCard=" + UID + "&idTerminal="
							+ URLEncoder.encode(terminalID, "UTF-8");
					JEditorPane cardWebsite = new JEditorPane(cardUrl);

					JFrame cardFrame = new JFrame("Browser RFID Detection");
					cardFrame.setSize(600, 300);
					cardFrame.setLocationRelativeTo(null);
					cardFrame.setResizable(false);
					JScrollPane jScrollPane1 = new JScrollPane(cardWebsite);
					cardFrame.add(jScrollPane1);
					cardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					sendUserPassage mySend = new sendUserPassage();
					mySend.run(UID);
					cardFrame.setVisible(true);
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cardFrame.setVisible(false);
					cardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				} catch (CardException e1) {
					e1.printStackTrace();
				}

			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mainFrame.setVisible(true);
			}
		}
	}
}