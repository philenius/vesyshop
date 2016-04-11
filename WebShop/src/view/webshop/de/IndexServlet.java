package view.webshop.de;

import javax.servlet.http.HttpServlet;

public class IndexServlet extends HttpServlet {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1867533624040259923L;

	@Override
	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {
		
		resp.sendRedirect("index.jsp");
	};
}
