package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FolderHelper {
	private static final Logger log = LogManager.getLogger(FolderHelper.class);
	
	public static List<File> getFiles(final File folder) {
		final List<File> files = new LinkedList<>();
		try {
			scanDirectory(folder, files);
		} catch (IOException e) {
			log.error(e);
		}
		return files;
	}

	private static void scanDirectory(final File rootDirectory, final List<File> storage) throws IOException {
		if (rootDirectory != null && rootDirectory.exists() && rootDirectory.isDirectory()) {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootDirectory.toPath())) {
				for (Path path : stream) {
					final File file = path.toFile();
					if (file.isDirectory()) {
						scanDirectory(file, storage);
					} else {
						storage.add(file);
					}
				}
			}
		}
	}

	public static void move(final Path source, final Path target) throws IOException {
		Files.createDirectories(target.getParent());
		Files.move(source, target);
	}

	public static void dropDirectoryIfEmpty(final File file) {
		if (file != null && file.isDirectory() && file.listFiles().length == 0) {
			file.delete();
		}
	}
}
