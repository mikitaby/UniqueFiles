package FileUnique;

/**
 * User: Mikita_Abramenka
 * Date: 3/19/13
 * Time: 6:56 PM
 */
class Loader {
    public static void main(String[] args) {
        final String START_PATH = "d:\\@Photo\\";

        Engine engine = new Engine(START_PATH);
        engine.process();
    }
}