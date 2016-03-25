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

@WebServlet("/admin/ModifyUser")
public class ModifyUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ModifyUser() {
		super();
	}

	public boolean isInteger(String str) {
		return str.matches("[0-9]");
	}

	public boolean isCorrectLength(String str, int maxLength) {
		return (str.length() <= maxLength);
	}

	public String createQueryUpdate(String[] args) {
		String start = "UPDATE employe SET ";
		String end = "WHERE e_ID = ?";
		String[] fields = { "e_Nom", "e_Prenom", "e_Poste", "e_lvlSecu" };
		String newValues = "";
		for (int i = 0; i < args.length; i++) {
			if (!args[i].isEmpty()) {
				newValues += fields[i] + " = ?, ";
			}
		}
		newValues = newValues.substring(0, newValues.length() - 2);
		newValues += " ";
		return start + newValues + end;
	}

	public int setFields(PreparedStatement ps, String[] args) throws SQLException {
		int nb = 1;
		for (int i = 0; i < args.length; i++) {
			if (!args[i].isEmpty()) {
				ps.setString(nb, args[i]);
				nb++;
			}
			if (!args[i].isEmpty() && i == args.length - 1)
				ps.setInt(nb, Integer.parseInt(args[i]));
		}
		return nb;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		PrintWriter out = Constants.HeaderShow(response, "Modifier Employe", false);

		String idCarte = request.getParameter("idCarte");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String poste = request.getParameter("poste");
		String lvlSecu = request.getParameter("lvlSecu");

		if (!lvlSecu.isEmpty() && !isInteger(lvlSecu) || idCarte.isEmpty() || !isCorrectLength(idCarte, 50)
				|| !isCorrectLength(nom, 25) || !isCorrectLength(prenom, 25) || !isCorrectLength(poste, 50)
				|| nom.isEmpty() && prenom.isEmpty() && poste.isEmpty() && lvlSecu.isEmpty()) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin/ModifyUser.html");
			out.println(
					"<font color=red><center>Formulaire invalide, veuillez respecter les consignes !</center></font>");
			rd.include(request, response);
		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
				String[] args = { nom, prenom, poste, lvlSecu };
				String sql1 = createQueryUpdate(args);
				ps = con.prepareStatement(sql1);
				int nb = setFields(ps, args);
				ps.setString(nb, idCarte);
				int result = ps.executeUpdate();
				if ((result > 0)) {
					out.println(
							"<h3><center>Informations de l'employe modifiees dans la base de donnees avec succes !</center></h3><br />");
					out.println("<h3><center>Recapitulatif :</center></h3>");
					String sql2 = "SELECT * FROM employe WHERE e_ID = ?";
					ps = con.prepareStatement(sql2);
					ps.setString(1, idCarte);
					rs = ps.executeQuery();
					if (rs.next()) {
						out.println("<h4><center>ID de la carte : " + rs.getString("e_ID") + "</center></h4>");
						out.println("<h4><center>Nom : " + rs.getString("e_Nom") + "</center></h4>");
						out.println("<h4><center>Prenom : " + rs.getString("e_Prenom") + "</center></h4>");
						out.println("<h4><center>Poste : " + rs.getString("e_Poste") + "</center></h4>");
						out.println("<h4><center>Niveau de securite : " + rs.getString("e_LvlSecu") + "</center></h4>");
					}
				} else {
					out.println(
							"<h3><font color=red><center>Erreur, impossible de modifier les informations de l'employe dans la base de donnees...<br />L'ID de la carte est-il correct?</center></font></h3>");
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