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
	protected HttpSession session;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.session = req.getSession();
		kPost(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.session = req.getSession();
		kGet(req, resp);
	}

	protected abstract void kPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
	protected abstract void kGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

	private LinkedList<String> getNotificationQueue(){
		LinkedList<String> queue = (LinkedList<String>) session.getAttribute("notificationQueue");
		if( queue == null){
			queue = new LinkedList<String>();
			session.setAttribute("notificationQueue", queue);
		}
		return queue;
	}

	protected void addNotification(String msg){
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
