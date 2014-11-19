package servlet;

import bean.Article;
import bean.Topic;
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

		int perPage = 24;
		int topicParam = -1;
		int authorParam = -1;
		String queryParam = null;

		int page = 1;
		String tmp;

		try{
			 tmp = request.getParameter("topic");
			if(tmp != null) topicParam = Integer.parseInt(tmp);
			tmp = request.getParameter("page");
			if(tmp != null) page = Integer.parseInt(tmp);
			tmp = request.getParameter("authorId");
			if(tmp != null) authorParam = Integer.parseInt(tmp);
		}catch (NumberFormatException ne){
			response.sendRedirect("home");
			return;
		}

		List<Topic> topicList = news.getTopics();
		NewsBeanRemote.PaginatedList list = news.getSearchPage(topicParam, authorParam, null, null, page, perPage);

		request.setAttribute("topics", topicList);
		request.setAttribute("topic", topicParam);
		request.setAttribute("page", page);
		request.setAttribute("prevPage", (page-1>0)?page-1:0);
		request.setAttribute("nextPage", (page < (int)Math.ceil((double)list.getTotal()/perPage))?page+1:0 );
		request.setAttribute("articles", (List<Article>) list.getPage());
		showNotifications(request);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/home.jsp");
		rd.forward(request, response);
	}
}
