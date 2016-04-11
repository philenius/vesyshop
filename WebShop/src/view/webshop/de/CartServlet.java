package view.webshop.de;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import mocking.Cake;
import mocking.Cart;

public class CartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8721110402254298502L;

	/**
	 * Returns all items in the shopping cart
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Cake> cakes = Cart.getCakes();
		
		String responseJSON = "[";
		
		for(int i = 0; i < cakes.size(); i++) {
			responseJSON += cakes.get(i).toJSON();
			if (i == (cakes.size() - 1)){
				continue;
			}
			responseJSON += ",";
		}
		
		responseJSON += " ]";
		
		resp.setContentType("application/json");
		
		PrintWriter out = resp.getWriter();
		out.write(responseJSON);
		out.close();		
	}
	
	/**
	 * Adds a new item to the shopping cart
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String jsonData = HelperClass.ReadJSONPostData(req);

		JSONObject object = new JSONObject(jsonData);
		long cake_id = object.getLong("cake_id");
		
		// TODO: Find cake and save in shopping cart
		Cart.addCake(new Cake(cake_id, null, null));

		resp.setStatus(HttpServletResponse.SC_CREATED);
		PrintWriter out = resp.getWriter();
		out.write("{\"itemCount\":" + Cart.getCakes().size() + "}");
		out.close();		
	}
	
	/**
	 * Removes an item from the shopping cart
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jsonData = HelperClass.ReadJSONPostData(req);

		JSONObject object = new JSONObject(jsonData);
		
		long cake_id = object.getLong("cake_id");
		
		// TODO: Find cake and save in shopping cart
		Cart.removeCake(cake_id);
		
		resp.setStatus(HttpServletResponse.SC_OK);
	}
}
