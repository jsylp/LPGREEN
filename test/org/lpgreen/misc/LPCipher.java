package org.lpgreen.misc;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Hex;

/**
 * It is the cipher class encapsulate the AES encryption/decryption. 
 * 
 * Creation date: Feb. 28, 2013
 * Last modify date: Feb. 28, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
public class LPCipher {

	// AES Encryption/Decryption
	SecretKeySpec keySpec;
	Cipher        cipher;

	public LPCipher() {
		try {
			// get the AES key generator
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			if (keyGen == null) {
				throw new Exception("No AES provider");
			}
			// random number generator
			SecureRandom sRand = SecureRandom.getInstance("SHA1PRNG");
			if (sRand == null) {
				throw new Exception("No SHA1PRNG provider");
			}

			String seed = "LPGREENSend1!#";
			sRand.setSeed(seed.getBytes());
			keyGen.init(128, sRand);

	        SecretKey key = keyGen.generateKey();
			keySpec = new SecretKeySpec(key.getEncoded(), "AES");   

			// get the AES engine class
			cipher = Cipher.getInstance("AES");
			if (cipher == null) {
				throw new Exception("No AES cipher");
			}
		}
		catch (Exception e) {
			System.out.println("LPCipher constructor Exception: " + e.getMessage());
		}
	}

	/**
	 * Encrypt the input clear text to a byte array 
	 * 
	 * @param clearText: input clear text
	 * @return byte array of cipher text
	 */
    public byte[] encryptToBytes(String clearText) throws Exception {
    	try {
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			return cipher.doFinal(clearText.getBytes());
    	}
    	catch (Exception e) {
			System.out.println("AES encryptToBytes exception: " + e.getMessage());
			return null;
    	}
    }

	/**
	 * Decrypt the input cipher bytes to a clear text 
	 * 
	 * @param encryptBytes: input cipher bytes
	 * @return clear text
	 */
    public String decryptFromBytes(byte[] encryptBytes) throws Exception {
    	try {
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			return new String(cipher.doFinal(encryptBytes));
    	}
		catch (Exception e) {
			System.out.println("AES decryptFromBytes exception: " + e.getMessage());
			return null;
		}
    }

	/**
	 * Encrypt the input clear text to a hex cipher text 
	 * 
	 * @param clearText: input clear text
	 * @return hex cipher text
	 */
    public String encrypt(String clearText) throws Exception {
    	try {
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			return Hex.encodeHexString(cipher.doFinal(clearText.getBytes()));
    	}
    	catch (Exception e) {
			System.out.println("AES encrypt exception: " + e.getMessage());
			return null;
    	}
    }

	/**
	 * Decrypt the input hex cipher text to a clear text 
	 * 
	 * @param encryptText: input hex cipher text
	 * @return clear text
	 */
    public String decrypt(String encryptText) throws Exception {
    	try {
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			return new String(cipher.doFinal(DatatypeConverter.parseHexBinary(encryptText)));
    	}
		catch (Exception e) {
			System.out.println("AES decrypt exception: " + e.getMessage());
			return null;
		}
    }

}
