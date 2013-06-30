package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileKey {
	private static final Logger log = LogManager.getLogger(FileKey.class);
	private final File file;
	private String hashSum = "";
	

	public FileKey(File file) {
		this.file = file;
	}

	private String getHashSum() throws KeyException {
		if (hashSum.isEmpty()) {
			hashSum = getHash();
		}
		return hashSum;
	}

	@Override
	public int hashCode() {
		//XXX: 2gb problem
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
			if (other.file != null)
				return false;
		} else
			try {
				// XXX: bad code
				if (!getHashSum().equals(other.getHashSum()))
					return false;
			} catch (KeyException e) {
				log.error(e);
				return false;
			}
		return true;
	}

	private String getHash() throws KeyException {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			try (FileInputStream fis = new FileInputStream(file)) {
				byte[] dataBytes = new byte[1024];
				int nread;
				while ((nread = fis.read(dataBytes)) != -1) {
					md.update(dataBytes, 0, nread);
				}
			}
			return bytesToString(md.digest());
		} catch (NoSuchAlgorithmException | IOException e) {
			throw new KeyException(e);
		}
	}

	private static String bytesToString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte aByte : bytes) {
			sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
