package servletUser;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class Db {
	private final String url = "jdbc:mysql://localhost/securfid";
	private final String user = "root";
	private final String pass = "root";

	public String identificationDb(String UID, String terminalID) {
		String nom = "", prenom = "", poste = "", result = "";
		Connection con = null;
		Statement statement = null;
		ResultSet resultatTotal = null;
		int secuPorte = 0, secuEmploye = 0;

		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection opened\n");
			statement = con.createStatement();
			resultatTotal = statement
					.executeQuery("SELECT e_Nom, e_Prenom, e_Poste, e_LvlSecu, p_LvlSecu " + "FROM employe, porte "
							+ "WHERE e_ID = \"" + UID + "\" " + "AND p_Terminal = \"" + terminalID + "\";");
			if (resultatTotal.next()) {
				nom = resultatTotal.getString("e_Nom");
				prenom = resultatTotal.getString("e_Prenom");
				poste = resultatTotal.getString("e_Poste");
				secuEmploye = resultatTotal.getInt("e_LvlSecu");
				secuPorte = resultatTotal.getInt("p_LvlSecu");
				result = nom + " " + prenom + "\n" + poste + "\nLv." + secuEmploye + "\n\n";
				if (secuEmploye >= secuPorte) {
					result += "ACCES AUTORISE";
				} else {
					result += "ACCES REFUSE NIVEAU DE SECURITE INSUFFISANT";
				}
			} else {
				result = "CARTE NON RECONNUE";
			}
			System.out.println(result);
		} catch (SQLException e) {
		} finally {
			if (con != null)
				try {
					/* Fermeture de la connexion */
					con.close();
					statement.close();
					System.out.println("\nConnection closed");
				} catch (SQLException ignore) {
					/* ignorer les erreurs a la fermeture */
				}
		}
		return result;
	}
}



