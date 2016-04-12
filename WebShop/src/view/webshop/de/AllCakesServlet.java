package view.webshop.de;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import mocking.Cake;

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
		
		List<Cake> cakes = Cake.getAllCakes();
		String responseJSON = "[";
		for (int i = 0; i < cakes.size(); i++) {
			responseJSON += cakes.get(i).toJSON();
			if (i == (cakes.size() - 1)) {
				continue;
			}
			responseJSON += ",";
		}
		responseJSON += " ]";

		PrintWriter out = resp.getWriter();
		out.write(responseJSON);
		out.close();

		try {
			// TODO: Serialize all cake objects into JSON

			resp.setContentType("application/json");
			
			resp.setStatus(HttpServletResponse.SC_OK);
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
