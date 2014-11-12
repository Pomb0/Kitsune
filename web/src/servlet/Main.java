package servlet;

import ejbInterface.AddBeanRemote;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Jaime
 * Date: 11/11/2014 - 07:28
 */
public class Main extends HttpServlet {
	@EJB
	private AddBeanRemote bean;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//try {
			//Context context = new InitialContext();
			//AddBeanRemote bean = (AddBeanRemote) context.lookup("global/ear_ear_exploded/ejb/AddBean!ejbInterface.AddBeanRemote");
			request.setAttribute("res", bean.add(25, 5));
		//} catch (NamingException e) {
		//		e.printStackTrace();
		//}

		request.setAttribute("msg", "hi there person.");
		System.out.println(request.toString());
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}
}
