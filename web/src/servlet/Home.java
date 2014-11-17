package servlet;

import ejbInterface.NewsBeanRemote;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Jaime
 * Date: 11/11/2014 - 07:28
 */
public class Home extends KitsuneServlet {
	@EJB
	private NewsBeanRemote news;

	protected void kPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void kGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!isLogged()){ //Divert the guests
			response.sendRedirect("login");
			return;
		}

		showNotifications();
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/home.jsp");
		rd.forward(request, response);
	}
}
