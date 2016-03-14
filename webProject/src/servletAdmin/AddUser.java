package servletAdmin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddUser() {
		super();
	}

	public boolean isInteger(String str) {
		return str.matches("[0-9]");
	}

	public boolean isCorrectLength(String str, int maxLength) {
		return (str.length() <= maxLength && !str.isEmpty());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String DB_URL = "jdbc:mysql://localhost/securfid";
		final String USER = "root";
		final String PASS = "root";
		Connection con = null;
		PreparedStatement ps = null;

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Ajouter un employe";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		out.println(docType + "<html>\n" + "<head><title>" + title + "</title></head>\n"
				+ "<body bgcolor=\"#f0f0f0\">\n" + "<h2 align=\"center\">" + title + "</h2>\n");

		String idCarte = request.getParameter("idCarte");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String poste = request.getParameter("poste");
		String lvlSecu = request.getParameter("lvlSecu");

		if (!isInteger(lvlSecu) || !isCorrectLength(idCarte, 50) || !isCorrectLength(nom, 25)
				|| !isCorrectLength(prenom, 25) || !isCorrectLength(poste, 50)) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin/AddUser.html");
			out.println("<font color=red><center>Formulaire invalide, veuillez respecter les consignes !</center></font>");
			rd.include(request, response); 
		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(DB_URL, USER, PASS);

				String sql = "INSERT INTO employe (e_ID, e_Nom, e_Prenom, e_Poste, e_LvlSecu) VALUES (?, ?, ?, ?, ?)";

				ps = con.prepareStatement(sql);
				ps.setString(1, idCarte);
				ps.setString(2, nom);
				ps.setString(3, prenom);
				ps.setString(4, poste);
				ps.setInt(5, Integer.parseInt(lvlSecu));
				int result = ps.executeUpdate();
				if ((result > 0)) {
					out.println("<h3><center>Employe ajoute a la base de donnees avec succes !</center></h3>");
				} else {
					out.println(
							"<h3><center>Erreur, impossible d'ajouter l'employe a la base de donnees...</center></h3>");
				}
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
}