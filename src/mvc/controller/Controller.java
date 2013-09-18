package mvc.controller;
/**
 * @author Artiom K., Jan R.B.
 */
import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aufgaben_db.Global;

import mvc.models.Pages;

public class Controller extends HttpServlet {
	
	/**
	 * eclipse wants that - so let's give a value.
	 */
	private static final long serialVersionUID = 1001L; //1001 nights.

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String buttonName = request.getParameter("q");
		String pageName = "";
		if(new File(Global.root + buttonName).exists()) {
			pageName = buttonName + ".jsp";
			
			new Pages().setPageName(pageName);
			RequestDispatcher view = request.getRequestDispatcher(pageName);
			view.forward(request, response);

		}
		else {
			//pageName = "404.jsp";
			System.out.print("Controller-Message: <h1>Error 404</h1>"
						+"<p>File not found.</p>");
		}
//		new Pages().setPageName(pageName);
//		RequestDispatcher view = request.getRequestDispatcher(pageName);
//		view.forward(request, response);
		
	}
}
