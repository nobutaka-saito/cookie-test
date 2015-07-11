package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 2195802688711027241L;
	private static final String ACCESS_COUNT_ATTR_KEY = "accessCount";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("Welcome!");
		if (session == null) {
			out.println("new session");
			session = request.getSession(true);
			session.setAttribute(ACCESS_COUNT_ATTR_KEY, Integer.valueOf(1));
			out.println("access count: " + 1);
		} else {
			out.println("sesion already exists.");
			int count = (Integer)session.getAttribute(ACCESS_COUNT_ATTR_KEY);
			session.setAttribute(ACCESS_COUNT_ATTR_KEY, count + 1);
			out.println("access count: " + (count + 1));
		}
	}
}
