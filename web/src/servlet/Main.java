package servlet;

import ejbInterface.AddBeanRemote;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Jaime
 * Date: 11/11/2014 - 07:28
 */
public class Main extends KitsuneServlet {
	@EJB
	private AddBeanRemote bean;

	protected void kPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void kGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("res", bean.add(25, 5));
		request.setAttribute("msg", "hi there person.");

		System.out.println(request.toString());
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/home.jsp");
		rd.forward(request, response);
	}
}
