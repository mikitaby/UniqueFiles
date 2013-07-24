package controller;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.filecomparer.FileKey;
import model.mover.Mover;
import model.mover.MoverFactorty;
import model.mover.MoverType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.FolderHelper;

class Loader {
	private static final Logger log = LogManager.getLogger(Loader.class);

	public static void main(String[] args) {
		processFolder("d:\\1\\");
	}

	private static void processFolder(final String path) {
		log.info("Start analysing...");

		final Map<FileKey, List<File>> storage = new HashMap<>();
		for (File file : FolderHelper.getFiles(new File(path))) {
			FileKey key = new FileKey(file);
			if (storage.containsKey(key)) {
				storage.get(key).add(file);
			} else {
				List<File> sameFiles = new LinkedList<>();
				sameFiles.add(file);
				storage.put(key, sameFiles);
			}
		}

		log.info("Finish analysing");

		for (List<File> sameFiles : storage.values()) {
			if (sameFiles.size() > 1) {
				long fullSize = 0;
				for (File file : sameFiles) {
					fullSize += file.length();
					log.trace(file.getAbsolutePath());
				}
				log.info("***************** " + fullSize);
			}
		}

		Mover mover = MoverFactorty.getMover(MoverType.Default, path);
		for (List<File> sameFiles : storage.values()) {
			mover.move(sameFiles);
		}
	}
}