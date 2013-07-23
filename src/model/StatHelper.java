package model;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatHelper {
	private static final Logger log = LogManager.getLogger(StatHelper.class);

	public static void show(Collection<List<File>> files) {
		for (List<File> sameFiles : files) {
			if (sameFiles.size() > 1) {
				long fullSize = 0;
				for (File file : sameFiles) {
					fullSize += file.length();
					log.trace(file.getAbsolutePath());
				}
				log.info("***************** " + fullSize);
			}
		}
	}
}
