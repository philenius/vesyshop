package view.webshop.de;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.mongodb.client.MongoDatabase;

import Connection.Connector;
import Kuchen.Cake;


public class AllCakesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2038518150653470055L;

	/**
	 * Retrieves all available items and returns them as JSON.
	 */
	@Override
	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {

		try {
			resp.setContentType("application/json");
			
			Connector c = new Connector();
			MongoDatabase db = c.getDatabase();
			String cakes = Cake.getAll(db);
			
			PrintWriter out = resp.getWriter();
			out.write(cakes);
			out.close();
			
			resp.setStatus(HttpServletResponse.SC_OK);
			c.close();
			return;
		} catch (JSONException ex) {
			System.out.println(ex);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	};

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}
