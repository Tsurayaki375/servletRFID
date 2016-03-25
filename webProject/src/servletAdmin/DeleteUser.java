package servletAdmin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Constants;

@WebServlet("/admin/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteUser() {
		super();
	}

	public boolean isCorrectLength(String str, int maxLength) {
		return (str.length() <= maxLength && !str.isEmpty());
	}

	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String nom = "";
		String prenom = "";
		String poste = "";
		int lvlSecu = -1;

		PrintWriter out = Constants.HeaderShow(response, "Supprimer Employe", false);

		String idCarte = request.getParameter("idCarte");

		if (!isCorrectLength(idCarte, 50)) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin/DeleteUser.html");
			out.println(
					"<font color=red><center>Formulaire invalide, veuillez respecter les consignes !</center></font>");
			rd.include(request, response);
		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);

				String sql1 = "SELECT e_Nom, e_Prenom, e_Poste, e_lvlSecu FROM employe WHERE e_ID = ?";
				String sql2 = "DELETE FROM employe WHERE e_ID = ?";

				ps = con.prepareStatement(sql1);
				ps.setString(1, idCarte);
				rs = ps.executeQuery();
				if (rs.next()) {
					nom = rs.getString("e_Nom");
					prenom = rs.getString("e_Prenom");
					poste = rs.getString("e_Poste");
					lvlSecu = rs.getInt("e_lvlSecu");
				}

				ps = con.prepareStatement(sql2);
				ps.setString(1, idCarte);
				int result = ps.executeUpdate();
				if ((result > 0)) {
					out.println(
							"<h3><center>Employe supprimer de la base de donnees avec succes !</center></h3><br />");
					out.println("<h3><center>Recapitulatif :</center></h3>");
					out.println("<h4><center>ID de la carte : " + idCarte + "</center></h4>");
					out.println("<h4><center>Nom : " + nom + "</center></h4>");
					out.println("<h4><center>Prenom : " + prenom + "</center></h4>");
					out.println("<h4><center>Poste : " + poste + "</center></h4>");
					out.println("<h4><center>Niveau de securite : " + lvlSecu + "</center></h4>");
				} else {
					out.println(
							"<h3><font color=red><center>Erreur, impossible de supprimer l'employe a la base de donnees...<br />L'ID de la carte est-il correct?</center></font></h3>");
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