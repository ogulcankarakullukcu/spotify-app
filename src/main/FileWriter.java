package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileWriter {

	private final String filePath = "/files/response.txt";
	private OutputStream os = null;

	public FileWriter() {

	}

	public boolean writeUsingOutputStream(String data) {

		try {
			os = new FileOutputStream(new File(filePath), true);
			os.write(data.getBytes(), 0, data.length());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}

	public void writeCsvFile(String data, String csvFilePath) {

		try {
			os = new FileOutputStream(new File(csvFilePath), false);
			OutputStreamWriter osw = new OutputStreamWriter(os,"utf-8");
			osw.write(data);
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
