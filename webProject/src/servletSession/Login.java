package servletSession;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String userID = "admin";
	private final String password = "password";

	public Login() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");

		if (userID.equals(user) && password.equals(pwd)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", "Dieu");
			session.setMaxInactiveInterval(5 * 60);
			Cookie userName = new Cookie("user", user);
			userName.setMaxAge(5 * 60);
			response.addCookie(userName);
			response.sendRedirect("LoginSuccess.jsp");
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red><center>Mauvais username ou password, veuillez reesayer.</center></font>");
			rd.include(request, response);
		}
	}
}
