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

import model.hash.HashEngine;
import model.length.LengthEngine;

public class SameFilesEngine {
	private static final Logger log = LogManager.getLogger(SameFilesEngine.class);
	private final String startPath;
	private final String startTempPath;
	private Collection<List<File>> collectionSameFiles;

	public SameFilesEngine(String startPath) {
		this.startPath = startPath;
		startTempPath = startPath + "tmpDouble" + File.separator;
	}

	public void analyse() {
		log.info(String.format("Star analyse folder %s", startPath));

		LengthEngine lengthEngine = new LengthEngine();
		lengthEngine.load(getListFiles(startPath));
		Collection<List<File>> collectionSameLengthFiles = lengthEngine.getCollectionSameFiles();
		
		HashEngine hashEngine = new HashEngine();		
		for (List<File> sameLengthFiles : collectionSameLengthFiles) {
			hashEngine.load(sameLengthFiles);
		}
		collectionSameFiles = hashEngine.getCollectionSameFiles();

		log.info(String.format("Finish analyse folder %s", startPath));
	}

	public void move() {
		for (List<File> sameFiles : collectionSameFiles) {
			boolean needMove = false;
			for (File file : sameFiles) {
				if (needMove) {
					Path target = Paths.get(file.getAbsolutePath().replace(startPath, startTempPath));
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

	private static List<File> getListFiles(String startPath) {
		List<File> files = new LinkedList<>();
		if (startPath != null && !startPath.trim().isEmpty()) {
			File startDir = new File(startPath);
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

	public void print() {
		for (List<File> sameFiles : collectionSameFiles) {
			log.trace("*****************");
			for (File file : sameFiles) {
				log.trace(file.getAbsolutePath());
			}
		}
	}
}
