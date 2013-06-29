package model.hash;

import java.io.File;

import model.KeyException;
import model.SameFiles;

public class HashEngine extends SameFiles<String> {

	@Override
	protected String getKey(File file) throws KeyException {
		return HashHelper.getHash(file);
	}
}