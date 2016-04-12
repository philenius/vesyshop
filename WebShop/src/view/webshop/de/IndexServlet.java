package view.webshop.de;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.client.MongoDatabase;

import Connection.Connector;
import Kuchen.Cake;
import Order.ShoppingCart;

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
		HttpSession session = req.getSession();
		String JSID  = session.getId();
		
		Connector connector = new  Connector();
		MongoDatabase db = connector.getDatabase();
		ShoppingCart cart = ShoppingCart.getBySessionId(JSID, db);
		
		// User does not have a cart yet
		if (cart == null) {
			ShoppingCart newCart = new ShoppingCart(new ArrayList<Cake>(), JSID);
			newCart.save(db);
		}

		resp.sendRedirect("resources/index.html");
	};

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}
