package bg.uni.sofia.fmi.grep;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MatchFinder implements Runnable {

	private File file;
	private String textToFind;

	private static final String COLON_SEPARATOR = ":";

	public MatchFinder(File file, String textToFind) {
		this.file = file;
		this.textToFind = textToFind;
	}

	@Override
	public void run() {
		String currentLine;
		int lineNumber = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while ((currentLine = reader.readLine()) != null) {
				lineNumber++;
				if (currentLine.contains(textToFind)) {
					System.out.println(file.toPath() + COLON_SEPARATOR + lineNumber + COLON_SEPARATOR + currentLine);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
