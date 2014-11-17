package servlet;

import bean.Article;
import ejbInterface.NewsBeanRemote;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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
		HttpSession session  = request.getSession();
		if(!isLogged(session)){ //Divert the guests
			response.sendRedirect("login");
			return;
		}

		List<Article> articleList = news.getArticlesPage(1, 1, 50);
		request.setAttribute("articles", articleList);
		showNotifications(request);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/home.jsp");
		rd.forward(request, response);
	}
}
