package constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletResponse;

public final class Constants {
	public static String DB_URL = "jdbc:mysql://localhost/securfid";
	public static String USER = "root";
	public static String PASS = "root";

	public static PrintWriter HeaderShow(HttpServletResponse response, String title, boolean show) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		out.println(docType + "<html>\n" + "<head><title>" + title + "</title></head>\n"
				+ "<body bgcolor=\"#f0f0f0\">\n" + "<h2 align=\"center\">" + title + "</h2>\n");

		if (show) {
			out.println("<style>");
			out.println("td {");
			out.println("text-align: center;");
			out.println("vertical-align: middle;");
			out.println("}");
			out.println("</style>");
		}
		return out;
	}

	public static void CreateShowTable(ResultSet rs, PrintWriter out, String query, String[] colsTitles,
			String[] formFields) throws SQLException {
		out.println("<table border=1 cellpadding=0 cellspacing=3 align=center>");

		out.println("<tr>");
		for (int i = 0; i < colsTitles.length; i++) {
			out.println("<td>" + colsTitles[i] + "</td>");
		}
		out.println("</tr>");

		while (rs.next()) {
			out.println("<tr>");

			for (int i = 0; i < formFields.length; i++) {
				out.println("<td>" + rs.getString(formFields[i]) + "</td>");
			}
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</body></html>");
	}

	public static void JDBCEnd(Connection conn, Statement stmt) {
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
