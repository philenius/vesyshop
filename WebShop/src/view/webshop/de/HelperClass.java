package view.webshop.de;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelperClass {

	/**
	 * Processes a HTTP request and returns the JSON data of the request.
	 * 
	 * @param request
	 *            The incoming HTTP request which shall be processed.
	 * @return Returns the JSON as string or an empty string in case of an
	 *         exception.
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

	/**
	 * Processes a given HTTP request and searches for a cookie called "SID". If
	 * this cookie is not present, then a new cookie with this name will be
	 * added to the response. If this cookie is present, nothing will be
	 * changed.
	 * 
	 * @param req
	 *            The incoming HTTP request.
	 * @param resp
	 *            The outgoing HTTP response.
	 * @return Returns the value of the session ID as string.
	 */
	public static String addSessionCookie(HttpServletRequest req, HttpServletResponse resp) {
		final String sessionName = "SID";

		Cookie cookies[] = req.getCookies();
		for (Cookie c : cookies) {
			if (c.getName().equals(sessionName)) {
				return c.getValue();
			}
		}

		String sessionValue = UUID.randomUUID().toString();
		Cookie sessionCookie = new Cookie(sessionName, sessionValue);
		sessionCookie.setMaxAge(60 * 60 * 10);
		resp.addCookie(sessionCookie);

		return sessionValue;
	}
}
