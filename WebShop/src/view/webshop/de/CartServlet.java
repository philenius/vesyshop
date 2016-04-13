package view.webshop.de;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoDatabase;

import Connection.Connector;
import Kuchen.Cake;
import Order.ShoppingCart;

public class CartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8721110402254298502L;

	/**
	 * Retrieves the cart of an user and returns it as JSON.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			// Creates session if there is none
			String JSID = req.getSession().getId();

			Connector connector = new Connector();
			MongoDatabase db = connector.getDatabase();
			ShoppingCart cart = ShoppingCart.getBySessionId(JSID, db);

			// User does not have a cart yet
			if (cart == null) {
				cart = new ShoppingCart(new ArrayList<Cake>(), JSID);
				cart.save(db);
			}

			resp.setContentType("application/json");

			PrintWriter out = resp.getWriter();
			out.write(cart.toJSON());
			out.close();

			resp.setStatus(HttpServletResponse.SC_OK);
			connector.close();
			return;
		} catch (JSONException ex) {
			System.out.println(ex);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * Adds a new item to the cart of an user.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			String jsonData = HelperClass.ReadJSONPostData(req);
			JSONObject jsonObject = new JSONObject(jsonData);

			// Creates session if there is none
			String JSID = req.getSession().getId();

			String cakeName = jsonObject.getString("cake");

			// cake data missing
			if (cakeName == null) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			Connector connector = new Connector();
			MongoDatabase db = connector.getDatabase();
			ShoppingCart cart = ShoppingCart.getBySessionId(JSID, db);

			// User does not have a cart yet
			if (cart == null) {
				cart = new ShoppingCart(new ArrayList<Cake>(), JSID);
			}
			cart.addCakeByName(cakeName, db);
			cart.save(db);

			resp.setStatus(HttpServletResponse.SC_CREATED);
			connector.close();
			return;
		} catch (JSONException ex) {
			System.out.println(ex);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * Removes an item from the cart of an user.
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			String jsonData = HelperClass.ReadJSONPostData(req);
			JSONObject jsonObject = new JSONObject(jsonData);

			// Creates session if there is none
			String JSID = req.getSession().getId();

			String cakeName = jsonObject.getString("cake");

			// cake data missing
			if (cakeName == null) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			Connector connector = new Connector();
			MongoDatabase db = connector.getDatabase();
			ShoppingCart cart = ShoppingCart.getBySessionId(JSID, db);

			// User does not have a cart yet
			if (cart == null) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			cart.removeCakeByName(cakeName,db);

			resp.setStatus(HttpServletResponse.SC_OK);
			connector.close();
			return;
		} catch (JSONException ex) {
			System.out.println(ex);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
