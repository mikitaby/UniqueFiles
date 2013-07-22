package controller;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.FileKey;

public class SameFilesEngine {

	public static Collection<List<File>> analyse(List<File> files) {
		Map<FileKey, List<File>> hm = new HashMap<>();
		for (File file : files) {
			FileKey key = new FileKey(file);
			if (hm.containsKey(key)) {
				hm.get(key).add(file);
			} else {
				List<File> sameFiles = new LinkedList<>();
				sameFiles.add(file);
				hm.put(key, sameFiles);
			}
		}
		return hm.values();
	}
}
