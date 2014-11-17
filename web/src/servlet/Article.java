package servlet;

import ejbInterface.NewsBeanRemote;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User: Jaime
 * Date: 17/11/2014 - 19:14
 */
public class Article extends KitsuneServlet{
	@EJB
	private NewsBeanRemote newsBean;
	@Override
	protected void kPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	protected void kGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession();
		String tmp;
		int articleId = 0;
		bean.Article article;
		if(!isLogged(session)){ //Divert the guests
			response.sendRedirect("login");
			return;
		}

		tmp = request.getParameter("id");
		if(tmp!=null){
			try{
				articleId = Integer.parseInt(tmp);
			}catch (NumberFormatException ne){
				response.sendRedirect("home");
				return;
			}
		}

		article = newsBean.getArticle(articleId);
		if(article == null) {
			response.sendRedirect("home");
			return;
		}

		if(article.getBody()!=null)article.setBody(article.getBody().replace("\n", "\n<br />"));

		request.setAttribute("article", article);

		showNotifications(request);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/article.jsp");
		rd.forward(request, response);
	}
}
