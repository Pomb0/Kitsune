package servlet;

import bean.User;
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
 * Date: 11/11/2014 - 08:25
 */
public class Login extends KitsuneServlet {
	@EJB
	private UserBeanRemote userBeanRemote;

	protected void kPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username!=null && password!=null){
			User user = userBeanRemote.auth(username, password);
			if(user != null){
				logIn(session, user.getId());
				String cena;
				response.sendRedirect("home");
				return;
			}else {
				addError(session, "Wrong username or password");
			}
		}
		response.sendRedirect("login");
	}

	protected void kGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showNotifications(request);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		rd.forward(request, response);
	}
}
