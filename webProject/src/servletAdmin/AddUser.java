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

import constants.Constants;

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
		Connection con = null;
		PreparedStatement ps = null;

		PrintWriter out = Constants.HeaderShow(response, "Ajouter Employe", false);

		String idCarte = request.getParameter("idCarte");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String poste = request.getParameter("poste");
		String lvlSecu = request.getParameter("lvlSecu");

		if (!isInteger(lvlSecu) || !isCorrectLength(idCarte, 50) || !isCorrectLength(nom, 25)
				|| !isCorrectLength(prenom, 25) || !isCorrectLength(poste, 50)) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin/AddUser.html");
			out.println(
					"<font color=red><center>Formulaire invalide, veuillez respecter les consignes !</center></font>");
			rd.include(request, response);
		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);

				String sql = "INSERT INTO employe (e_ID, e_Nom, e_Prenom, e_Poste, e_LvlSecu) VALUES (?, ?, ?, ?, ?)";

				ps = con.prepareStatement(sql);
				ps.setString(1, idCarte);
				ps.setString(2, nom);
				ps.setString(3, prenom);
				ps.setString(4, poste);
				ps.setInt(5, Integer.parseInt(lvlSecu));
				int result = ps.executeUpdate();
				if ((result > 0)) {
					out.println("<h3><center>Employe ajoute a la base de donnees avec succes !</center></h3><br />");
					out.println("<h3><center>Recapitulatif :</center></h3>");
					out.println("<h4><center>ID de la carte : " + idCarte + "</center></h4>");
					out.println("<h4><center>Nom : " + nom + "</center></h4>");
					out.println("<h4><center>Prenom : " + prenom + "</center></h4>");
					out.println("<h4><center>Poste : " + poste + "</center></h4>");
					out.println("<h4><center>Niveau de securite : " + lvlSecu + "</center></h4>");
				} else {
					out.println(
							"<h3><font color=red><center>Erreur, impossible d'ajouter l'employe a la base de donnees...<br />L'ID de la carte existe t-il deja?</center></font></h3>");
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