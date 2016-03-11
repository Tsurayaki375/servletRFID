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

/**
 * Servlet implementation class ShowUsers
 */
@WebServlet("/admin/ShowUsers")
public class ShowUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String DB_URL = "jdbc:mysql://localhost/securfid";
		final String USER = "root";
		final String PASS = "root";
		Connection conn = null;
		Statement stmt = null;

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Employes Database";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		out.println(docType + "<html>\n" + "<head><title>" + title + "</title></head>\n"
				+ "<body bgcolor=\"#f0f0f0\">\n" + "<h2 align=\"center\">" + title + "</h2>\n");
		
		out.println("<style>");
		out.println("td {");
		out.println("text-align: center;");
		out.println("vertical-align: middle;");	 
		out.println("}");		 
		out.println("</style>");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT e_ID, e_Nom, e_Prenom, e_Poste, e_LvlSecu  FROM employe";
			ResultSet rs = stmt.executeQuery(sql);

			out.println("<table border=1 cellpadding=0 cellspacing=3 align=center>");

			out.println("<tr>");
			out.println("<td> ID :</td>");
			out.println("<td> Nom :</td>");
			out.println("<td> Prenom :</td>");
			out.println("<td> Poste :</td>");
			out.println("<td> Niveau de securite :</td>");
			out.println("</tr>");

			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getString("e_ID") + "</td>");
				out.println("<td>" + rs.getString("e_Nom") + "</td>");
				out.println("<td>" + rs.getString("e_Prenom") + "</td>");
				out.println("<td>" + rs.getString("e_Poste") + "</td>");
				out.println("<td>" + rs.getInt("e_LvlSecu") + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</body></html>");

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}