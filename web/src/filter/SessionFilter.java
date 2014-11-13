package filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * User: Jaime
 * Date: 11/11/2014 - 08:36
 */
public class SessionFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {

	}

}
