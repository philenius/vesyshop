package view.webshop.de;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

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
		String jsonData = HelperClass.ReadJSONPostData(req);

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			String user = jsonObject.getString("user");
			String password = jsonObject.getString("password");

			// User or password missing
			if ((user == null) || (password == null)) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			System.out.println("user: " + user);
			System.out.println("password: " + password);
			
			String JSID = req.getSession().getId();

			// TODO: Check user and pw in DB and connect JSID

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
