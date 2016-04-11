package view.webshop.de;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

public class HelperClass {

	public static String ReadJSONPostData(HttpServletRequest request) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String jsonData = "";
			try {
				String line;
				while ((line = bufferedReader.readLine()) != null)
					jsonData += line;
					System.out.println(jsonData);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return jsonData;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "";
	}

}
