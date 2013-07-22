package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FolderHelper {
	private static final Logger log = LogManager.getLogger(FolderHelper.class);

	private final String basePath;
	private final List<File> files;

	public FolderHelper(final String basePath) {
		this.basePath = basePath;
		files = new LinkedList<>();
	}

	public List<File> getFiles() {
		scanDirectory(new File(basePath));
		return files;
	}

	public void move(final Collection<List<File>> files) {
		for (List<File> sameFiles : files) {
			if (sameFiles.size() > 1) {
				boolean needMove = false;
				for (File file : sameFiles) {
					if (needMove) {
						Path target = Paths.get(file.getAbsolutePath().replace(basePath, basePath + "tmpDouble" + File.separator));
						try {
							Path tmpFolder = target.getParent();
							if (!Files.exists(tmpFolder)) {
								Files.createDirectories(tmpFolder);
							}
							Files.move(Paths.get(file.getAbsolutePath()), target);
							if (file.getParentFile().listFiles().length == 0) {
								file.getParentFile().delete();
							}
						} catch (IOException e) {
							log.error(e);
						}
					}
					needMove = true;
				}
			}
		}
	}

	private void scanDirectory(File rootDirectory) {
		File[] filesInDirectory = rootDirectory.listFiles();
		for (File file : filesInDirectory) {
			if (file.isDirectory()) {
				scanDirectory(file);
			} else {
				files.add(file);
			}
		}
	}
}
