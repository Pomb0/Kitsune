package servlet;

import ejbInterface.UserBeanRemote;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User: Jaime
 * Date: 17/11/2014 - 18:53
 */
public class Users extends KitsuneServlet{

	@EJB
	private UserBeanRemote userBean;
	@Override
	protected void kPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	protected void kGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession();
		if(!isLogged(session)){ //Divert the guests
			response.sendRedirect("login");
			return;
		}
		if(!getUser(session).isAdmin()){
			addError(session, "You're not admin enough.");
			response.sendRedirect("home");
			return;
		}

		request.setAttribute("userList", userBean.getUserList());

		showNotifications(request);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/users.jsp");
		rd.forward(request, response);
	}
}
