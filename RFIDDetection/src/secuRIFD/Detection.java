package secuRIFD;

import javax.swing.*;
import javax.smartcardio.CardException;
import java.io.IOException;
import java.net.URLEncoder;

public class Detection {
	private ScanUID myScanUID = new ScanUID();
	private int id = 0;

	public Detection(int id) {
		this.id = id;
	}

	public void run() throws IOException {

		String UID = "";
		String terminalID = "";

		if (myScanUID.isCardHere()) {
			myScanUID.openConnection(id);
			try {
				UID = myScanUID.getUidCard();
				terminalID = myScanUID.getTerminalID(id);
				myScanUID.getATRcard();

				String url = "http://localhost:50001/webProject/RFIDAuthorization?idCard=" + UID + "&idTerminal="
						+ URLEncoder.encode(terminalID, "UTF-8");
				JEditorPane website = new JEditorPane(url);

				JFrame frame = new JFrame("Browser RFID Detection");
				frame.setSize(400, 400);
				frame.setResizable(false);
				JScrollPane jScrollPane = new JScrollPane(website);
				frame.add(jScrollPane);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

			} catch (CardException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			String url = "http://localhost:50001/webProject/RFIDAuthorization";
			JEditorPane website = new JEditorPane(url);

			JFrame frame = new JFrame("Browser RFID Detection");
			frame.setSize(400, 400);
			frame.setResizable(false);
			JScrollPane jScrollPane = new JScrollPane(website);
			frame.add(jScrollPane);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
	}
}