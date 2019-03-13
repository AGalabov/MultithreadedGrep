package bg.uni.sofia.fmi.grep;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {

		if(args.length<3) {
			System.out.println("Usage is: <path> <text> <maxThreadCnt>");
			return;
		}

		Path path = Paths.get(args[0]);
		String textToSearch = args[1];
		int threadsCount = Integer.parseInt(args[2]);

		try (GrepEngine mySearch = new GrepEngine(path, textToSearch, threadsCount)) {
			mySearch.searchAllFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
