package FileUnique;

import java.io.File;

/**
 * User: Mikita_Abramenka Date: 3/19/13 Time: 7:35 PM
 */
class Engine {
	private final String startPath;
	private final SameHashFiles sameHashFiles;

	public Engine(String startPath) {
		this.startPath = startPath;
		sameHashFiles = new SameHashFiles();
	}

	public void process() {
		System.out.println(String.format("Path: %s", startPath));
		SameLengthFiles sameLengthFiles = new SameLengthFiles();
		sameLengthFiles.loadFilesCollection(new File(startPath));
		System.out.println("Files processed: " + sameLengthFiles.getFilesCount());
		System.out.println("sameLengthFiles groups: " + sameLengthFiles.getStorage().size());
		for (Iterable<HashFile> equalLengthFiles : sameLengthFiles.getStorage().values()) {
			sameHashFiles.parse(equalLengthFiles);
		}
		System.out.println("Same Files groups: " + sameHashFiles.getStorage().size());
		sameHashFiles.print();
	}

	public void save() {
		// save sameHashFiles
	}
}
