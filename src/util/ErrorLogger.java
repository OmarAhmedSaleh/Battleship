package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorLogger {
	private static String path = "src/logs.txt";
	private static String newLine = "--------------------------------------------------------\n";
	private ErrorLogger() {}
	
	public static void log(StackTraceElement[] elements) {
		try {
			createNewLogsFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
			for(StackTraceElement element: elements) {
				writer.write(element.toString() + "\n");
			}
			writer.write(newLine);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void createNewLogsFile() {
		try {
			File file = new File(path);
			file.createNewFile();		
		}catch(IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
}
