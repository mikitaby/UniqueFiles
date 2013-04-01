package FileUnique;

/**
 * User: Mikita_Abramenka
 * Date: 3/21/13
 * Time: 4:42 PM
 */
class SameLengthFiles extends SameFiles<Long> {

    @Override
    protected Long getKey(HashFile hashFile) {
        return hashFile.length();
    }
}
