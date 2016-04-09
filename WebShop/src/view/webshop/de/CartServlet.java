package view.webshop.de;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class CartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8721110402254298502L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String jsonData = HelperClass.ReadJSONPostData(req);

		JSONObject object = new JSONObject(jsonData);
		int cake_id = object.getInt("cake_id");
		
		// TODO: Find cake and save in shopping cart

		resp.setStatus(HttpServletResponse.SC_CREATED);

	}
}
