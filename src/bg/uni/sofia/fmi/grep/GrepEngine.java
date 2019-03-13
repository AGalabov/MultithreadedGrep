package bg.uni.sofia.fmi.grep;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GrepEngine implements AutoCloseable {

	private Path path;
	private final String text;
	private ExecutorService executor;

	private static final String TXT_FORMAT = ".txt";

	public GrepEngine(Path path, String text, int threadsCount) {
		this.path = path;
		this.text = text;
		executor = Executors.newFixedThreadPool(threadsCount);
	}

	private void search(File file) {
		if (!file.isDirectory()) {
			if (file.canRead() && file.getName().endsWith(TXT_FORMAT)) {
				executor.execute(new MatchFinder(file, text));
			}
		} else {
			File[] filesList = file.listFiles();
			if (filesList != null) {
				for (File currentFile : filesList) {
					search(currentFile);
				}
			}
		}
	}

	public void searchAllFiles() {
		search(path.toFile());
	}

	@Override
	public void close() throws Exception {
		executor.shutdown();

		try {
			while (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
