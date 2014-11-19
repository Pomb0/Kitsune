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
 * Date: 17/11/2014 - 11:13
 */
public class Profile extends KitsuneServlet {
	@EJB
	UserBeanRemote userBean;
	@Override
	protected void kPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(!isLogged(session)){ //Divert the guests
			response.sendRedirect("login");
			return;
		}
		int idVal = 0;

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passveri = request.getParameter("passveri");
		String mail = request.getParameter("mail");
		String delete = request.getParameter("deleteId");
		boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));

		if(delete == null && id!=null && name!=null && username!=null && mail!=null ) {
			try {
				idVal = Integer.parseInt(id);
				User user = new User()
						.setId(idVal)
						.setUsername(username)
						.setMail(mail)
						.setName(name)
						.setAdmin(isAdmin);

				if(password!=null && passveri != null && password.length()>0){
					if(password.equals(passveri)) {
						user.setPassword(password);
					}else{
						addError(session, "The passwords do not match.");
						response.sendRedirect("profile?id=" + idVal);
						return;
					}
				}

				UserBeanRemote.Response outcome = userBean.updateUser(user);
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
						addAchievement(session, "User updated.");
						break;
				}
			}catch (NumberFormatException ne){
				addError(session, "Could not edit account.");
			}
		}else if(delete != null){
			try {
				int delId = Integer.parseInt(delete);
				userBean.removeUser(delId);
				addAchievement(session, "User removed.");
			}catch (NumberFormatException ne){
				addError(session, "Error deleting account.");
			}
		}
		response.sendRedirect("profile?id=" + idVal);
	}

	@Override
	protected void kGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(!isLogged(session)){ //Divert the guests
			response.sendRedirect("login");
			return;
		}
		int accountId = 0;
		User user = null;

		try{
			accountId = Integer.parseInt(request.getParameter("id"));
		}catch (NumberFormatException ne){
			accountId = 0;
		}

		user = userBean.getUser(accountId);
		if(accountId == 0 || user==null){
			response.sendRedirect("profile?id=" +  getUserId(session));
			return;
		}

		request.setAttribute("puser", user);

		showNotifications(request);

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/profile.jsp");
		rd.forward(request, response);
	}
}
