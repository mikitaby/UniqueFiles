package controller;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Loader {
	private static final Logger log = LogManager.getLogger(Loader.class);
	
	public static void main(String[] args) {
		log.info("Start analysing...");
		FolderHelper folderHelper = new FolderHelper("d:\\1\\");
		Collection<List<File>> files = SameFilesEngine.analyse(folderHelper.getFiles());
		log.info("Finish analysing");
		StatHelper.show(files);		
		
		for (List<File> sameFiles : files) {
			folderHelper.moveExceptFirst(sameFiles);
		}		
	}
}