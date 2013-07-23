package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FolderHelper {
	private static final Logger log = LogManager.getLogger(FolderHelper.class);

	public static List<File> getFiles(final File folder) {
		final List<File> files = new LinkedList<>();
		scanDirectory(folder, files);
		return files;
	}

	private static void scanDirectory(final File rootDirectory, final List<File> storage) {
		final File[] filesInDirectory = rootDirectory.listFiles();
		for (File file : filesInDirectory) {
			if (file.isDirectory()) {
				scanDirectory(file, storage);
			} else {
				storage.add(file);
			}
		}
	}

	public static void moveIntoTempFolder(final File file, final String basePath) {
		Path target = Paths.get(file.getAbsolutePath().replace(basePath, basePath + "tmpDouble" + File.separator));
		try {
			Files.createDirectories(target.getParent());
			Files.move(Paths.get(file.getAbsolutePath()), target);
			dropEmptyFolder(file.getParentFile());
		} catch (IOException e) {
			log.error(e);
		}
	}

	private static void dropEmptyFolder(final File folder) {
		if (folder.isDirectory() && folder.listFiles().length == 0) {
			folder.delete();
		}
	}
}
