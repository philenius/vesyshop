package view.webshop.de;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;

import mocking.Cake;

public class AllCakes extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2038518150653470055L;

	@Override
	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {
		List<Cake> cakes = Cake.getAllCakes();
		
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
	};
}
