package model.comparerengine.hash;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.FolderHelper;
import model.comparerengine.ComparerEngine;

public class HashFileComparor implements ComparerEngine {
	private static final Logger log = LogManager.getLogger(HashFileComparor.class);

	@Override
	public Collection<List<File>> getSameFiles(final String path) {
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
		return storage.values();
	}
}
