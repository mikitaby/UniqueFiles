package controller;

import java.io.File;
import java.util.Collection;
import java.util.List;

import model.comparerengine.ComparerEngine;
import model.comparerengine.ComparerEngineFactorty;
import model.comparerengine.ComparerEngineType;
import model.mover.Mover;
import model.mover.MoverFactorty;
import model.mover.MoverType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Loader {
	private static final Logger log = LogManager.getLogger(Loader.class);

	public static void main(String[] args) {
		processFolder("d:\\1\\");
	}

	private static void processFolder(final String path) {
		ComparerEngine comparerEngine = ComparerEngineFactorty.getComparerEngine(ComparerEngineType.Default);
		Collection<List<File>> cls = comparerEngine.getSameFiles(path);

		for (List<File> sameFiles : cls) {
			if (sameFiles.size() > 1) {
				long fullSize = 0;
				for (File file : sameFiles) {
					fullSize += file.length();
					log.trace(file.getAbsolutePath());
				}
				log.info("***************** " + fullSize);
			}
		}

		Mover mover = MoverFactorty.getMover(MoverType.Default, path);
		for (List<File> sameFiles : cls) {
			mover.move(sameFiles);
		}
	}
}