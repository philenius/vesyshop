package view.webshop.de;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

public class HelperClass {

	/**
	 * Processes a HTTP request and returns the JSON data of the request.
	 * @param request	The incoming HTTP request which shall be processed.
	 * @return 			Returns the JSON as string or an empty string in case of an exception.
	 */
	public static String ReadJSONPostData(HttpServletRequest request) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String jsonData = "";
			if (bufferedReader != null) {
				try {
					jsonData = bufferedReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return jsonData;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "";
	}

}
