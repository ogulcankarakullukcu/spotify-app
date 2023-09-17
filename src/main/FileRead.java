package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class FileRead {

	private String filePath = "/files/response.txt";

	public FileRead() {

	}

	public String read() throws IOException {

		StringBuilder response = new StringBuilder();
		String line = "";

		BufferedReader br;
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));

			while ((line = br.readLine()) != null) {

				response.append(line);

			}

		} catch (IOException e) {

			e.printStackTrace();
			throw new IOException();

		}

		return response.toString();
	}

	public Set<String> csvFileRead(String filePath) throws Exception {
		String line = "";
		Set<String> result = new HashSet<String>();

		try {

			BufferedReader br = new BufferedReader(new FileReader(filePath));

			while ((line = br.readLine()) != null) {

				result.add(line);

			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("File Not Found!");
		}

		return result;

	}

}
