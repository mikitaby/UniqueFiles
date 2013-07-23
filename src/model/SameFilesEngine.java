package model;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SameFilesEngine {

	public static Collection<List<File>> analyse(List<File> files) {
		final Map<FileKey, List<File>> storage = new HashMap<>();
		for (File file : files) {
			FileKey key = new FileKey(file);
			if (storage.containsKey(key)) {
				storage.get(key).add(file);
			} else {
				List<File> sameFiles = new LinkedList<>();
				sameFiles.add(file);
				storage.put(key, sameFiles);
			}
		}
		return storage.values();
	}
}
