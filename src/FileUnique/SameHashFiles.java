package FileUnique;

/**
 * User: Mikita_Abramenka
 * Date: 3/21/13
 * Time: 4:47 PM
 */
class SameHashFiles extends SameFiles<String> {

    @Override
    protected String getKey(HashFile hashFile) throws GetKeyException {
        return hashFile.getHash();
    }
}