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

@WebServlet("/admin/ShowUsers")
public class ShowUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowUsers() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;

		PrintWriter out = Constants.HeaderShow(response, "Employes Database", true);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);

			stmt = conn.createStatement();
			String sql = "SELECT * FROM employe";
			ResultSet rs = stmt.executeQuery(sql);

			String[] colsTitles = { "ID de la carte :", "Nom :", "Prenom :", "Poste :", "Niveau de securite :" };
			String[] formFields = { "e_ID", "e_Nom", "e_Prenom", "e_Poste", "e_lvlSecu" };

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