package FileUnique;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class SameFiles<K> {
	private final Map<K, List<HashFile>> sameObjectStorage = new HashMap<>();
	private final Map<K, HashFile> uniqueStorage = new HashMap<>();
	private long filesCount;
	private static Logger logger = LogManager.getLogger(SameFiles.class);

	protected void add(HashFile hashFile, K key) {
		List<HashFile> sameValuesList = sameObjectStorage.get(key);
		if (sameValuesList == null) {
			HashFile sameObject = uniqueStorage.remove(key);
			if (sameObject != null) {
				List<HashFile> value = new LinkedList<>();
				value.add(sameObject);
				value.add(hashFile);
				sameObjectStorage.put(key, value);
			} else {
				uniqueStorage.put(key, hashFile);
			}
		} else {
			sameValuesList.add(hashFile);
		}
		filesCount++;
	}

	public Map<K, List<HashFile>> getStorage() {
		return sameObjectStorage;
	}

	public void parse(Iterable<HashFile> collection) {
		uniqueStorage.clear();
		for (HashFile hashFile : collection) {
			try {
				add(hashFile, getKey(hashFile));
				filesCount++;
			} catch (GetKeyException e) {
				e.printStackTrace();
			}
		}
	}

	protected abstract K getKey(HashFile hashFile) throws GetKeyException;

	protected void print() {
		for (List<HashFile> sameFiles : sameObjectStorage.values()) {
			logger.trace("*****************");
			for (HashFile hashFile : sameFiles) {
				logger.trace(hashFile.getAbsolutePath());
			}
		}
	}

	protected long getFilesCount() {
		return filesCount;
	}
}
