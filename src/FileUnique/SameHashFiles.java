package FileUnique;

class SameHashFiles extends SameFiles<String> {
	@Override
	protected String getKey(HashFile hashFile) throws GetKeyException {
		return hashFile.getHash();
	}
}