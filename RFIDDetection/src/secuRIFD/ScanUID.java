package secuRIFD;

import java.util.List;
import javax.smartcardio.*;

public class ScanUID {
	private boolean okConnection = false;
	List<CardTerminal> terminals = null;
	TerminalFactory factory;
	CardTerminals cardterminals;
	CardTerminal terminal;
	private String terminalID;

	public String getTerminalID(int id) {
		terminalID = terminals.get(id).getName();
		return terminalID;
	}

	public ScanUID() {
		factory = TerminalFactory.getDefault();
		cardterminals = factory.terminals();
		try {
			terminals = cardterminals.list();
		} catch (CardException e) {
			e.printStackTrace();
		}
		card = null;
	}

	public boolean isOkConnection() {
		return okConnection;
	}

	public void setOkConnection(boolean okConnection) {
		this.okConnection = okConnection;
	}

	private static Card card = null;
	private static CardChannel channel = null;

	public boolean isCardHere() {
		try {
			if (terminals.get(0).isCardPresent()) {
				return true;
			}
		} catch (CardException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public String openConnection(int id) {
		String result = "";
		if (isCardHere()) {
			result += "Card detected!\n";
			try {
				card = terminals.get(id).connect("T=1");
			} catch (CardException e) {
				e.printStackTrace();
			}
			result += "Card:" + card + "\n";
			channel = card.getBasicChannel();
			result += "Channel:" + channel + "\n";
		}
		return result;

	}

	/**
	 * The ATR (Answer To Reset) conveys information about the communication
	 * parameters proposed by the card, and the card's nature and state.
	 */
	public String getATRcard() throws CardException {
		String result = "";
		if (isCardHere()) {
			result += "Connection open!\n";
			ATR atr = card.getATR();
			byte[] baAtr = atr.getBytes();
			result += "ATR = 0x";
			for (int i = 0; i < baAtr.length; i++) {
				result += String.format("%02X", baAtr[i]);
			}
		}
		return result;
	}

	/**
	 * Each smart card contains an integrated chip with a unique permanent
	 * identification number burned-in during the manufacturing process : UID.
	 */
	public String getUidCard() throws CardException {
		String result = "";
		if (isCardHere()) {
			byte[] cmdApduGetCardUid = new byte[] { (byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
			ResponseAPDU respApdu = channel.transmit(new CommandAPDU(cmdApduGetCardUid));
			if (respApdu.getSW1() == 0x90 && respApdu.getSW2() == 0x00) {
				byte[] baCardUid = respApdu.getData();
				for (int i = 0; i < baCardUid.length; i++) {
					result += String.format("%02X", baCardUid[i]);
				}
			}
		}
		return result;
	}
}