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
@WebServlet("/admin/ShowGates")
public class ShowGates extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowGates() {
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
		String title = "Portes Database";
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
			sql = "SELECT p_Terminal, p_LvlSecu FROM porte";
			ResultSet rs = stmt.executeQuery(sql);

			out.println("<TABLE BORDER=1 CELLPADDING=0 CELLSPACING=3 ALIGN=CENTER>");

			out.println("<TR>");
			out.println("<TD> ID Terminal :</TD>");
			out.println("<TD> Niveau de securite :</TD>");
			out.println("</TR>");

			while (rs.next()) {
				out.println("<TR>");
				out.println("<TD>" + rs.getString("p_Terminal") + "</TD>");
				out.println("<TD>" + rs.getString("p_LvlSecu") + "</TD>");
				out.println("</TR>");
			}
			out.println("</TABLE>");
			out.println("</BODY></HTML>");

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