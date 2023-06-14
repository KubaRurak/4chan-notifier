package com.Godel.channotifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class JsonUtils {

	public static String urlToJsonString(String siteAddress) throws IOException {
		URL url = new URL(siteAddress);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuilder responseBuilder = new StringBuilder();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			responseBuilder.append(inputLine);
		}
		in.close();
		String jsonStr = responseBuilder.toString();
		return jsonStr;
	}

}
