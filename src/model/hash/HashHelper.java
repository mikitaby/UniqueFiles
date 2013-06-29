package model.hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.KeyException;

public class HashHelper {

	public static String getHash(File file) throws KeyException {
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