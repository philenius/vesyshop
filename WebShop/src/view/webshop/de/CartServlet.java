package view.webshop.de;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import mocking.Cake;
import mocking.Cart;

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

		
		List<Cake> cakes = Cart.getCakes();
		String json = "[";
		for (int i = 0; i < cakes.size(); i++) {
			json += cakes.get(i).toJSON();
			if (i == (cakes.size() - 1)) {
				continue;
			}
			json += ",";
		}
		json += "]";

		PrintWriter out = resp.getWriter();
		out.write(json);
		out.close();
		
		try {
			String JSID = req.getSession().getId();
			
			// TODO: Search in DB for the cart associated with this JSID and return
			// it

			resp.setContentType("application/json");
			
			resp.setStatus(HttpServletResponse.SC_OK);
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

		String jsonData = HelperClass.ReadJSONPostData(req);

		try {
			JSONObject jsonObject = new JSONObject(jsonData);

			int cakeID = -1;
			cakeID = jsonObject.getInt("cake_id");

			// cakeID missing
			if (cakeID == -1) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			// TODO: Find cake and save in shopping cart

			Cart.addCake(new Cake(cakeID, "", new BigDecimal(Math.floor(Math.random() * 100))));

			resp.setStatus(HttpServletResponse.SC_CREATED);
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
		String jsonData = HelperClass.ReadJSONPostData(req);
		
		try {
			JSONObject jsonObject = new JSONObject(jsonData);

			int cakeID = -1;
			cakeID = jsonObject.getInt("cake_id");

			// cakeID missing
			if (cakeID == -1) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			// TODO: Find cake and remove it from shopping cart
			String JSID = req.getSession().getId();
			Cart.getCakes().remove(0);

			resp.setStatus(HttpServletResponse.SC_OK);
			return;
		} catch (JSONException ex) {
			System.out.println(ex);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
