package model.length;

import java.io.File;

import model.SameFiles;

public class LengthEngine extends SameFiles<Long> {
	@Override
	protected Long getKey(File file) {
		return file.length();
	}
}
