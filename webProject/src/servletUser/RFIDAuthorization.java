package servletUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RFIDAuthorization")
public class RFIDAuthorization extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RFIDAuthorization() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");

		String paramIdCardValue = request.getParameter("idCard");
		String paramIdTerminalValue = request.getParameter("idTerminal");

		PrintWriter out = response.getWriter();
		Db myDb = new Db();

		if (paramIdCardValue == null && paramIdTerminalValue == null) {
			out.println(
					"<h3><center>Bonjour, veuillez passer votre carte devant le lecteur pour vous indentifier...</center></h3>");
		} else {
			paramIdTerminalValue = URLDecoder.decode(request.getParameter("idTerminal"), "UTF-8");
			out.println(
					"<h3><center>" + myDb.identificationDb(paramIdCardValue, paramIdTerminalValue) + "</center></h3>");
		}
		out.close();
	}
}
