package servletUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RFIDAuthorization
 */
@WebServlet("/RFIDAuthorization")
public class RFIDAuthorization extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RFIDAuthorization() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
	
		String paramIdCardValue = request.getParameter("idCard");
		String paramIdTerminalValue = request.getParameter("idTerminal");
		
		PrintWriter out = response.getWriter();
		
		if(paramIdCardValue == null && paramIdTerminalValue == null){
			out.println("<h3>Bonjour, veuillez passer votre carte devant le lecteur pour entrer...</h3>");	
		}
		else{
			paramIdTerminalValue = URLDecoder.decode(request.getParameter("idTerminal"), "UTF-8");
			Db myDb = new Db();
			out.println("<h3>" + myDb.identificationDb(paramIdCardValue, paramIdTerminalValue) + "</h3>");
		}
		out.close();	
	}
}
