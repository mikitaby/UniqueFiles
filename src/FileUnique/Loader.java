package FileUnique;

class Loader {
	public static void main(String[] args) {
		final String START_PATH = "d:\\@Photo\\";

		Engine engine = new Engine(START_PATH);
		engine.process();
		engine.save();
	}
}