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

@WebServlet("/admin/ShowGates")
public class ShowGates extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowGates() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;

		PrintWriter out = Constants.HeaderShow(response, "Portes Database", true);

		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);

			stmt = conn.createStatement();
			String sql = "SELECT * FROM porte";
			ResultSet rs = stmt.executeQuery(sql);

			String[] colsTitles = { "ID Terminal :", "Niveau de securite :" };
			String[] formFields = { "p_Terminal", "p_LvlSecu" };

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