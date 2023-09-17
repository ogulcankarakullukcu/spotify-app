package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Operator {

	private FileWriter fileWriter;
	private final String BASE_URL = "https://api.spotify.com/v1/";
	private final String authCode = "Bearer BQBhiN1aIPcy-pRmdS_yxnrB2UgBrwSKPng1pb6ftal7iOJpSQdM6QxAmhK7O4rZljfWQ-7hpJ-Khem1s34ogb8yVHg-_JTfdX1iY07Hr3FqYV1DA3A";

	
	public Operator() {
		this.fileWriter = new FileWriter();
	}

	public boolean searchTrack(String searchWord) throws IOException {
		URL obj = new URL(BASE_URL + "search?q=track:" + searchWord + "&type=track");

		HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setRequestProperty("Authorization", authCode);
		int responseCode = httpURLConnection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			String inputLine;
			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				response.append("\n");
			}
			in.close();

			return fileWriter.writeUsingOutputStream(response.toString());
		} else {
			return false;
		}

	}

}
