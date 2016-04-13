package view.webshop.de;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoDatabase;

import Connection.Connector;
import Kunde.User;

public class RegisterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6808035424949277851L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * Handles registration of a new user.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			String jsonData = HelperClass.ReadJSONPostData(req);
			JSONObject jsonObject = new JSONObject(jsonData);
			
			String user = jsonObject.getString("user");
			String password = jsonObject.getString("password");

			// User or password missing
			if ((user == null) || (password == null)) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			System.out.println(user + ":" + password);
			Connector connector = new Connector();
			MongoDatabase db = connector.getDatabase();
			
			// TODO: User newUser = new User(user, password, shoppingCart); -> no ShoppingCart shall be required
//			User newUser = new User(user, password);
//			newUser.save(db);
			
			resp.setStatus(HttpServletResponse.SC_OK);
			connector.close();
			return;
		}
		catch(JSONException ex) {
			System.out.println(ex);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
