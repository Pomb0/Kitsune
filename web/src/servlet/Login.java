package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Jaime
 * Date: 11/11/2014 - 08:25
 */
public class Login extends KitsuneServlet {
	protected void kPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void kGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		rd.forward(request, response);
	}
}
