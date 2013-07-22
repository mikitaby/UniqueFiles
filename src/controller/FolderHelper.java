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
	
	public FolderHelper(final String basePath) {
		this.basePath = basePath;
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
	
	public List<File> getListFiles(final String basePath) {
		List<File> files = new LinkedList<>();
		if (basePath != null && !basePath.trim().isEmpty()) {
			File startDir = new File(basePath);
			if (startDir != null && startDir.exists()) {
				if (startDir.isDirectory()) {
					File[] listFiles = startDir.listFiles();
					if (listFiles != null) {
						for (File file : listFiles) {
							files.addAll(getListFiles(file.getAbsolutePath()));
						}
					}
				} else {
					files.add(startDir);
				}
			}
		}
		return files;
	}
}
