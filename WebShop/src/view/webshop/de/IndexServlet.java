package view.webshop.de;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1867533624040259923L;

	/**
	 * Returns the actual Single Page Application (SPA).
	 */
	@Override
	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {

		// Creates session if there is none
		req.getSession();

		resp.sendRedirect("resources/index.html");
	};

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}
