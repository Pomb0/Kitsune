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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
		Integer topicParam = null;
		Integer authorParam = null;
		String queryParam = null;
		Date date = null;
		String dataStr = null;
		String params = null;

		int page = 1;
		String tmp;

		try{
			tmp = request.getParameter("page");
			if(tmp != null) page = Integer.parseInt(tmp);
		}catch (NumberFormatException ne){
			page = 1;
		}

		try{
			tmp = request.getParameter("topic");
			if(tmp != null){
				topicParam = Integer.parseInt(tmp);
			}
		}catch (NumberFormatException ne){
			topicParam = null;
		}

		try{
			tmp = request.getParameter("authorId");
			if(tmp != null) authorParam = Integer.parseInt(tmp);
		}catch (NumberFormatException ne){
			authorParam = null;
		}

		try{
			queryParam = request.getParameter("query");
		}catch (NumberFormatException ne){
			queryParam = null;


		}try{
			dataStr = request.getParameter("dateTime");
			if(dataStr!=null) date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(dataStr);
		} catch (ParseException e) {
			date = null;
			addError(session, "Invalid date format.");
		}

		if(topicParam!=null && topicParam==-1) topicParam = null;
		if(authorParam!=null && authorParam==-1) authorParam = null;

		List<Topic> topicList = news.getTopics();
		NewsBeanRemote.PaginatedList list = news.getSearchPage(topicParam, authorParam, date, queryParam, page, perPage);

		switch (list.getType()){
			case NORMAL:
				params = "";
				break;
			case TOPIC:
				params = "topic=" + topicParam;
				break;
			case AUTHOR:
				params = "authorId=" + authorParam;
				break;
			case TEXT_SEARCH:
				params = "query=" + queryParam;
				break;
			case DATE:
				params = "dateTime=" + dataStr;
				break;
		}

		request.setAttribute("query", queryParam);
		request.setAttribute("dateTime", dataStr);

		request.setAttribute("params", params);
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
