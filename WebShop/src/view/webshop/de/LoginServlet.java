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

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1176564469751724435L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * Handles login of an user.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			String jsonData = HelperClass.ReadJSONPostData(req);
			JSONObject jsonObject = new JSONObject(jsonData);

			// Creates session if there is none
			String JSID = req.getSession().getId();

			String user = jsonObject.getString("user");
			String password = jsonObject.getString("password");

			// User or password missing
			if ((user == null) || (password == null)) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			System.out.println("user: " + user);
			System.out.println("password: " + password);
			System.out.println("JSID: " + JSID);
			
			Connector connector = new Connector();
			MongoDatabase db = connector.getDatabase();
			
			// TODO: User foundUser = User.getByName(user, db) throws NullPointerException;

//			User foundUser = User.getByName(user, db);
//			// Wrong password
//			if (!foundUser.checkPw(password)){
//				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
//				return;	
//			}
//			foundUser.session = JSID;
//			foundUser.save(db);
			
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
