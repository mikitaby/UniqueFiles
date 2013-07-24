package model.comparerengine.hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileKey {
	private static final Logger log = LogManager.getLogger(FileKey.class);
	private final File file;
	private byte[] hashSum;

	public FileKey(final File file) {
		this.file = file;
	}

	@Override
	public int hashCode() {
		// XXX: 2gb problem
		return (int) file.length();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileKey other = (FileKey) obj;
		if (file == null) {
			return other.file == null;
		} else {
			// XXX: bad code
			try {
				return Arrays.equals(getHashSum(), other.getHashSum());
			} catch (NoSuchAlgorithmException | IOException e) {
				log.error(e);
				return false;
			}
		}
	}

	private byte[] getHashSum() throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		if (hashSum == null) {
			hashSum = calculateMD5Hash();
		}
		return hashSum;
	}

	private byte[] calculateMD5Hash() throws NoSuchAlgorithmException, IOException, FileNotFoundException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		try (FileInputStream fis = new FileInputStream(file)) {
			byte[] dataBytes = new byte[1024];
			int nread;
			while ((nread = fis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}
		}
		return md.digest();
	}
}
