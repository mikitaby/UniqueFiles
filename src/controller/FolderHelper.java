package controller;

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

	private final String basePath;

	public FolderHelper(final String basePath) {
		this.basePath = basePath;		
	}

	public List<File> getFiles() {
		final List<File> files = new LinkedList<>();
		scanDirectory(new File(basePath), files);
		return files;
	}

	public void moveExceptFirst(final List<File> files) {
		if (files != null && files.size() > 1) {
			boolean isFirst = true;
			for (File file : files) {
				if (!isFirst) {
					moveIntoTempFolder(file);
				}
				isFirst = false;
			}
		}
	}

	private void moveIntoTempFolder(final File file) {
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
}
