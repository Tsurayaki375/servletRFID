package secuRIFD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendUserPassage {
	public String calculStatut(int lvlSecuEmp, int lvlSecuPorte) {
		if (lvlSecuEmp >= lvlSecuPorte)
			return "autorise";
		else
			return "non autorise";
	}

	@SuppressWarnings("resource")
	public void run(String UID, String terminalID) {
		final String url = "jdbc:mysql://localhost/securfid";
		final String user = "root";
		final String pass = "root";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String nom = "";
		String prenom = "";
		int lvlSecuEmp = 0;
		int lvlSecuPorte = 0;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String currentDate = (dateFormat.format(date));

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);

			String sql1 = "SELECT e_Nom, e_Prenom, e_LvlSecu FROM employe WHERE e_ID = ?";
			String sql2 = "SELECT p_lvlSecu FROM porte WHERE p_Terminal = ?";
			String sql3 = "INSERT INTO historique (h_ID, h_Terminal, h_Date, h_Statut, h_Nom, h_Prenom) VALUES (?, ?, ?, ?, ?, ?)";

			ps = con.prepareStatement(sql1);
			ps.setString(1, UID);
			rs = ps.executeQuery();
			if (rs.next()) {
				nom = rs.getString("e_Nom");
				prenom = rs.getString("e_Prenom");
				lvlSecuEmp = rs.getInt("e_lvlSecu");
			}

			ps = con.prepareStatement(sql2);
			ps.setString(1, terminalID);
			rs = ps.executeQuery();
			if (rs.next()) {
				lvlSecuPorte = rs.getInt("p_lvlSecu");
			}
			
			String statut = calculStatut(lvlSecuEmp, lvlSecuPorte);

			ps = con.prepareStatement(sql3);
			ps.setString(1, UID);
			ps.setString(2, terminalID);
			ps.setString(3, currentDate);
			ps.setString(4, statut);
			ps.setString(5, nom);
			ps.setString(6, prenom);
			ps.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException se2) {
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
