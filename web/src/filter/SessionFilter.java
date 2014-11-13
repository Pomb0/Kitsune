package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User: Jaime
 * Date: 11/11/2014 - 08:36
 */
public class SessionFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		//HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();

		req.setAttribute("isLogged", true);
		req.setAttribute("sessionId", session.getId());

		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {

	}

}
