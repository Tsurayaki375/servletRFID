package servletAdmin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Constants;

@WebServlet("/admin/ShowPassages")
public class ShowPassages extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowPassages() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;

		PrintWriter out = Constants.HeaderShow(response, "Historique Database");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);

			stmt = conn.createStatement();
			String sql = "SELECT h_ID, h_Date, h_Nom, h_Prenom FROM historique ORDER BY h_Date ASC";
			ResultSet rs = stmt.executeQuery(sql);

			String[] colsTitles = { "ID de la carte :", "Date de passage :", "Nom :", "Prenom :" };
			String[] formFields = { "h_ID", "h_Date", "h_Nom", "h_Prenom" };

			Constants.CreateShowTable(rs, out, sql, colsTitles, formFields);

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Constants.JDBCEnd(conn, stmt);
		}
	}
}
