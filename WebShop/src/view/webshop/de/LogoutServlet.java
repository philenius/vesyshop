package view.webshop.de;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.client.MongoDatabase;

import Connection.Connector;
import Kunde.User;
import Order.ShoppingCart;

public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3760120267160814105L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * Handles logout of an user.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			// Creates session if there is none
			String JSID = req.getSession().getId();

			Connector connector = new Connector();
			MongoDatabase db = connector.getDatabase();

			ShoppingCart cart = ShoppingCart.getBySessionId(JSID, db);
			if (cart != null) {
				User user = User.getBySID(JSID, db);
				user.clearSession(db);
				cart.delete(db);
			}

			// Destroys the session
			HttpSession session = req.getSession();
			session.invalidate();
			
			resp.setStatus(HttpServletResponse.SC_OK);
			connector.close();
			return;
		} catch (Exception ex) {
			System.out.println(ex);
		}
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}