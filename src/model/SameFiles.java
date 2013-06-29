package model;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SameFiles<K> {
	private static final Logger log = LogManager.getLogger(SameFiles.class);
	private final Map<K, List<File>> storage = new HashMap<>();

	protected abstract K getKey(File file) throws KeyException;

	public void load(Iterable<File> files) {
		final Map<K, File> uniqueStorage = new HashMap<>();
		for (File file : files) {
			try {
				K key = getKey(file);
				if (storage.containsKey(key)) {
					List<File> sameValues = storage.get(key);
					sameValues.add(file);
				} else {
					if (uniqueStorage.containsKey(key)) {
						File baseFile = uniqueStorage.remove(key);
						List<File> sameValues = new LinkedList<>();
						sameValues.add(baseFile);
						sameValues.add(file);
						storage.put(key, sameValues);
					} else {
						uniqueStorage.put(key, file);
					}
				}
			} catch (KeyException e) {
				log.error(e);
			}
		}
	}

	public Collection<List<File>> getCollectionSameFiles() {
		return storage.values();
	}
}