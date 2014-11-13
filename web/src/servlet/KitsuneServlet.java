package servlet;

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
	private HttpSession session;
	private HttpServletResponse response;
	private HttpServletRequest request;
	private Integer userID;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.session = req.getSession();
		this.request = req;
		this.response = resp;
		kPost(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.session = req.getSession();
		this.request = req;
		this.response = resp;
		kGet(req, resp);
	}

	protected abstract void kPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
	protected abstract void kGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

	protected boolean isLogged(){
		if(session.getAttribute("userID")!=null) return true;
		return false;
	}

	protected void logIn(int id){
		session.setAttribute("userID", id);
		userID = id;
	}

	protected void logOut(){
		session.removeAttribute("userID");
	}

	protected int getUserId(){
		Integer id = (Integer) session.getAttribute("userID");
		return (id!=null)?(int)id:-1;
	}

	protected void showNotifications(){
		if(hasNotifications()) {
			List<String> prev = (List<String>) request.getAttribute("notifications");
			List<String> novas = getNotifications();
			if(prev!=null) novas.addAll(prev);
			request.setAttribute("notifications", novas);
		}
	}

	private LinkedList<String> getNotificationQueue(){
		LinkedList<String> queue = (LinkedList<String>) session.getAttribute("notificationQueue");
		if( queue == null){
			queue = new LinkedList<String>();
			session.setAttribute("notificationQueue", queue);
		}
		return queue;
	}

	protected void addError(String msg){
		msg = "<span class=\"red\">" + msg + "</span>";
		addEntry(msg);
	}

	protected void addAchievement(String msg){
		msg = "<span class=\"green\">" + msg + "</span>";
		addEntry(msg);
	}

	protected void addNotification(String msg){
		msg = "<span class=\"platinum\">" + msg + "</span>";
		addEntry(msg);
	}

	private void addEntry(String msg){
		LinkedList<String> queue = getNotificationQueue();
		queue.add(msg);
	}

	protected boolean hasNotifications(){
		LinkedList<String> queue = getNotificationQueue();
		return !queue.isEmpty();
	}

	protected List<String> getNotifications(){
		LinkedList<String> queue = getNotificationQueue();
		session.removeAttribute("notificationQueue");
		return queue;
	}
}
