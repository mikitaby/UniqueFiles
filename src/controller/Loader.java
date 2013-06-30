package controller;

class Loader {
	public static void main(String[] args) {
		SameFilesEngine sameFilesEngine = new SameFilesEngine("d:\\Video\\");
		sameFilesEngine.analyse();
		//sameFilesEngine.move();
		sameFilesEngine.print();
	}
}