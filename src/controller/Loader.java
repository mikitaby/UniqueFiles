package controller;

import java.io.File;
import java.util.Collection;
import java.util.List;

class Loader {
	public static void main(String[] args) {
		
		FolderHelper folderHelper = new FolderHelper("d:\\1\\");
		Collection<List<File>> files = SameFilesEngine.analyse(folderHelper.getListFiles("d:\\1\\"));
		StatHelper.show(files);		
		//folderHelper.move(files);
	}
}