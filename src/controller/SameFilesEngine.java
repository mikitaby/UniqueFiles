package controller;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.FileKey;

public class SameFilesEngine {
	private static final Logger log = LogManager.getLogger(SameFilesEngine.class);

	public static Collection<List<File>> analyse(List<File> files) {
		log.info("Start analysing...");
		Map<FileKey, List<File>> hm = new HashMap<>();
		for (File file : files) {
			FileKey key = new FileKey(file);
			if (hm.containsKey(key)) {
				hm.get(key).add(file);
			} else {
				LinkedList<File> sameFiles = new LinkedList<>();
				sameFiles.add(file);
				hm.put(key, sameFiles);
			}
		}

		log.info("Finish analysing");
		return hm.values();
	}	
}
