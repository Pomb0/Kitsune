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
 * Date: 18/11/2014 - 14:25
 */
public class Author extends KitsuneServlet{

	@EJB
	NewsBeanRemote newsBean;

	@Override
	protected void kPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	protected void kGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession();
		if(!isLogged(session)){ //Divert the guests
			response.sendRedirect("login");
			return;
		}
		String tmp;
		int authorId;
		tmp = request.getParameter("id");

		if(tmp!=null){
			try {
				authorId = Integer.parseInt(tmp);
				response.sendRedirect("home?authorId=" + authorId);
				return;
			}catch (NumberFormatException e){
				authorId = -1;
			}
		}


		request.setAttribute("authorList", newsBean.getAuthors());

		showNotifications(request);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/authors.jsp");
		rd.forward(request, response);
	}
}
