package view.webshop.de;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mocking.Cake;
import mocking.Ingredient;

public class RegisterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6808035424949277851L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Cake c = new Cake(1, "Cheese cake", new BigDecimal(12.34));
		Ingredient i1 = new Ingredient("Milk", 0.125f);
		c.addIngredient(i1);		
		response.setContentType("application/json");
		
			PrintWriter out = response.getWriter();
			out.write(c.toJSON());
			out.close();
//		response.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
	}
}
