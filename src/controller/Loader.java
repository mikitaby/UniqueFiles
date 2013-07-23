package controller;

import java.io.File;
import java.util.Collection;
import java.util.List;

import model.FolderHelper;
import model.Mover;
import model.MoverFabrique;
import model.SameFilesEngine;
import model.StatHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Loader {
	private static final Logger log = LogManager.getLogger(Loader.class);
	
	public static void main(String[] args) {
		final String PATH = "d:\\1\\";
		log.info("Start analysing...");
		Collection<List<File>> files = SameFilesEngine.analyse(FolderHelper.getFiles(new File(PATH)));
		log.info("Finish analysing");
		StatHelper.show(files);		
		Mover mover = MoverFabrique.getMover("Default", PATH);
		for (List<File> sameFiles : files) {
			mover.move(sameFiles);
		}		
	}
}