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
			String SID = HelperClass.addSessionCookie(req, resp);

			String user = jsonObject.getString("user");
			String password = jsonObject.getString("password");

			// User or password missing
			if ((user == null) || (password == null)) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			
			Connector connector = new Connector();
			MongoDatabase db = connector.getDatabase();
			
			User foundUser = User.getByName(user, db);

			// User does not exist
			if (foundUser == null) {
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;		
			}
			
			// Wrong password
			if (!foundUser.checkPw(password)){
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;	
			}

			foundUser.session = SID;
			foundUser.updateSession(db);
			
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
