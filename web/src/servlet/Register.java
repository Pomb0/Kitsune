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
 * Date: 12/11/2014 - 10:37
 */
public class Register extends KitsuneServlet {

	@EJB
	private UserBeanRemote userBeanRemote;

	protected void kPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passveri = request.getParameter("passveri");
		String mail = request.getParameter("mail");

		if(!password.equals(passveri)) addError(session, "The passwords do not match.");

		if(name!=null && username!=null && password!=null && passveri!=null && mail!=null && password.equals(passveri)){
			UserBeanRemote.Response outcome = userBeanRemote.register(username, password, mail, name);
			switch (outcome){
				case DUPLICATE_USER:
					addError(session, "The username " + username + " is already taken.");
					break;
				case DUPLICATE_MAIL:
					addError(session, "The mail " + mail + " is already taken.");
					break;
				case UNKNOWN:
					addError(session, "An error occurred, try again, possibly with a different username and or mail.");
					break;
				case OK:
					addAchievement(session, "Account created with success.");
					response.sendRedirect("login");
					return;
			}
		}
		showNotifications(request);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/register.jsp");
		rd.forward(request, response);
	}

	protected void kGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/register.jsp");
		rd.forward(request, response);
	}

}
