package servlet;

import bean.User;
import ejbInterface.UserBeanRemote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Jaime
 * Date: 13/11/2014 - 07:03
 */

public abstract class KitsuneServlet extends HttpServlet {
	@EJB
	private UserBeanRemote userBean;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		noCache(resp);
		if(isLogged(session)) req.getSession().setAttribute("user", getUser(session));
		kPost(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		noCache(resp);
		if(isLogged(session)) req.getSession().setAttribute("user", getUser(session));
		kGet(req, resp);
	}

	protected abstract void kPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
	protected abstract void kGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

	protected boolean isLogged(HttpSession session){
		return session.getAttribute("userID") != null;
	}

	protected void logIn(HttpSession session, int id){
		session.setAttribute("userID", id);
	}

	protected void logOut(HttpSession session){
		session.removeAttribute("userID");
	}

	protected int getUserId(HttpSession session){
		Integer id = (Integer) session.getAttribute("userID");
		return (id!=null)?(int)id:-1;
	}

	protected void showNotifications(HttpServletRequest request){
		if(hasNotifications(request.getSession())) {
			List<String> prev = (List<String>) request.getAttribute("notifications");
			List<String> novas = getNotifications(request.getSession());
			if(prev!=null) novas.addAll(prev);
			request.setAttribute("notifications", novas);
		}
	}

	private LinkedList<String> getNotificationQueue(HttpSession session){
		LinkedList<String> queue = (LinkedList<String>) session.getAttribute("notificationQueue");
		if( queue == null){
			queue = new LinkedList<String>();
			session.setAttribute("notificationQueue", queue);
		}
		return queue;
	}

	protected void addError(HttpSession session, String msg){
		msg = "<span class=\"red\">" + msg + "</span>";
		addEntry(session, msg);
	}

	protected void addAchievement(HttpSession session, String msg){
		msg = "<span class=\"green\">" + msg + "</span>";
		addEntry(session, msg);
	}

	protected void addNotification(HttpSession session, String msg){
		msg = "<span class=\"platinum\">" + msg + "</span>";
		addEntry(session, msg);
	}

	private void addEntry(HttpSession session, String msg){
		LinkedList<String> queue = getNotificationQueue(session);
		queue.add(msg);
	}

	protected boolean hasNotifications(HttpSession session){
		LinkedList<String> queue = getNotificationQueue(session);
		return !queue.isEmpty();
	}

	protected List<String> getNotifications(HttpSession session){
		LinkedList<String> queue = getNotificationQueue(session);
		session.removeAttribute("notificationQueue");
		return queue;
	}

	protected void noCache(HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
	}

	protected User getUser(HttpSession session){
		User user;
		int userID = getUserId(session);
		if(userID==-1) user = null;
		user =  userBean.getUser(userID);
		if(user==null) logOut(session);
		return user;
	}
}
