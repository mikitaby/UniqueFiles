package FileUnique;

import java.io.File;

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

	public void loadFilesCollection(File startDirectory) {
		if (startDirectory != null && startDirectory.exists() && startDirectory.isDirectory()) {
			File[] listFiles = startDirectory.listFiles();
			if (listFiles != null) {
				for (File file : listFiles) {
					if (file.isDirectory()) {
						loadFilesCollection(file);
					} else {
						add(new HashFile(file.getAbsolutePath()), file.length());
					}
				}
			}
		}
	}
}
