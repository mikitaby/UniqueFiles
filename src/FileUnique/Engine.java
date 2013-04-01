package FileUnique;

import java.io.File;

/**
 * User: Mikita_Abramenka Date: 3/19/13 Time: 7:35 PM
 */
class Engine {
	private final String startPath;

	public Engine(String startPath) {
		this.startPath = startPath;
	}

	private static void loadFilesCollection(File startDirectory, SameLengthFiles files) {
		if (startDirectory != null && startDirectory.exists() && startDirectory.isDirectory()) {
			File[] listFiles = startDirectory.listFiles();
			if (listFiles != null) {
				for (File file : listFiles) {
					if (file.isDirectory()) {
						loadFilesCollection(file, files);
					} else {
						files.add(new HashFile(file.getAbsolutePath()), file.length());
					}
				}
			}
		}
	}

	public void process() {
		System.out.println(String.format("Path: %s", startPath));		
		SameLengthFiles sameLengthFiles = new SameLengthFiles();
		loadFilesCollection(new File(startPath), sameLengthFiles);
		System.out.println("Files processed: " + sameLengthFiles.getFilesCount());
		System.out.println("sameLengthFiles groups: " + sameLengthFiles.getStorage().size());

		SameHashFiles sameHashFiles = new SameHashFiles();
		for (Iterable<HashFile> equalLengthFiles : sameLengthFiles.getStorage().values()) {
			sameHashFiles.parse(equalLengthFiles);
		}
		System.out.println("Same Files groups: " + sameHashFiles.getStorage().size());
		sameHashFiles.print();
	}
}
