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
	@SuppressWarnings("resource")
	public void run(String UID) {
		final String url = "jdbc:mysql://localhost/securfid";
		final String user = "root";
		final String pass = "root";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String nom = "";
		String prenom = "";

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String currentDate = (dateFormat.format(date));

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);

			String sql1 = "SELECT e_Nom, e_Prenom FROM employe WHERE e_ID = ?";
			String sql2 = "INSERT INTO historique (h_ID, h_Date, h_Nom, h_Prenom) VALUES (?, ?, ?, ?)";

			ps = con.prepareStatement(sql1);
			ps.setString(1, UID);
			rs = ps.executeQuery();
			if (rs.next()) {
				nom = rs.getString("e_Nom");
				prenom = rs.getString("e_Prenom");
			}

			ps = con.prepareStatement(sql2);
			ps.setString(1, UID);
			ps.setString(2, currentDate);
			ps.setString(3, nom);
			ps.setString(4, prenom);
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
