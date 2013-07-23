package model;

import java.io.File;
import java.util.List;

public class DefaultMover implements Mover {

	final String basePath;
	
	public DefaultMover(final String basePath) {
		this.basePath = basePath;
	}
	
	@Override
	public void move(List<File> sameFiles) {
		if (sameFiles != null && sameFiles.size() > 1) {
			boolean isFirst = true;
			for (File file : sameFiles) {
				if (!isFirst) {
					FolderHelper.moveIntoTempFolder(file, basePath);
				}
				isFirst = false;
			}
		}
	}
}
