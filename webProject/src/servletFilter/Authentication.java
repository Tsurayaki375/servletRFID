package servletFilter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/Authentication")
public class Authentication implements Filter {

	private ServletContext context;

	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		this.context.log("Requested Resource::" + uri);

		HttpSession session = req.getSession(false);

		if (session == null && !(uri.endsWith("html") || uri.endsWith("Login") || uri.contains("RFIDAuthorization") ||  uri.contains("images"))) {
			this.context.log("Unauthorized access request");
			res.sendRedirect("http://localhost:50001/webProject/login.html");
		} else {
			((HttpServletResponse) response).setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			((HttpServletResponse) response).setHeader("Pragma", "no-cache");
			((HttpServletResponse) response).setDateHeader("Expires", 0);
			chain.doFilter(req, res);
		}
	}

	public void destroy() {

	}
}