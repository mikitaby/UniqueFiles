package FileUnique;

import java.util.*;

/**
 * User: Mikita_Abramenka
 * Date: 3/21/13
 * Time: 3:40 PM
 */
abstract class SameFiles<K> {
    private final Map<K, List<HashFile>> sameObjectStorage = new HashMap<>();
    private final Map<K, HashFile> uniqueStorage = new HashMap<>();
    private long filesCount;

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
    		System.out.println("*****************");
    		for (HashFile hashFile : sameFiles) {
    			System.out.println(hashFile.getAbsolutePath());
    		}
    	}    		
    }
    
    protected long getFilesCount() {
    	return filesCount;
    }
}
