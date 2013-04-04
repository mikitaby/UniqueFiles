package FileUnique;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Engine {
	private final String startPath;
	private final SameHashFiles sameHashFiles;
	private static Logger logger = LogManager.getLogger(Engine.class);

	public Engine(String startPath) {
		this.startPath = startPath;
		sameHashFiles = new SameHashFiles();
	}

	public void process() {
		logger.trace(String.format("Path: %s", startPath));
		SameLengthFiles sameLengthFiles = new SameLengthFiles();
		sameLengthFiles.loadFilesCollection(new File(startPath));
		logger.trace("Files processed: " + sameLengthFiles.getFilesCount());
		logger.trace("sameLengthFiles groups: " + sameLengthFiles.getStorage().size());
		for (Iterable<HashFile> equalLengthFiles : sameLengthFiles.getStorage().values()) {
			sameHashFiles.parse(equalLengthFiles);
		}
		logger.trace("Same Files groups: " + sameHashFiles.getStorage().size());
		sameHashFiles.print();
		logger.trace("End!");
	}

	public void save() {
		// save sameHashFiles
	}
}
