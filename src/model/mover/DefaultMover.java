package model.mover;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.FolderHelper;

class DefaultMover implements Mover {
	
	private static final Logger log = LogManager.getLogger(Mover.class);
	private final String basePath;
	
	public DefaultMover(final String basePath) {
		this.basePath = basePath;
	}
	
	@Override
	public void move(List<File> sameFiles) {
		if (sameFiles != null && sameFiles.size() > 1) {
			boolean isFirst = true;
			for (File file : sameFiles) {
				if (!isFirst) {
					try {
						FolderHelper.move(file.toPath(), Paths.get(file.getAbsolutePath().replace(basePath, basePath + "tmpDouble" + File.separator)));
						FolderHelper.dropDirectoryIfEmpty(file.getParentFile());
					} catch (IOException e) {
						log.error(e);
					}
				}
				isFirst = false;
			}
		}
	}
}

