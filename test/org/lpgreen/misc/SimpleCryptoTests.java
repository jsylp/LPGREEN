package org.lpgreen.misc;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * Usage:
 * <pre>
 * String crypto = SimpleCrypto.encrypt(masterpassword, cleartext)
 * ...
 * String cleartext = SimpleCrypto.decrypt(masterpassword, crypto)
 * </pre>
 * @author ferenc.hechler
 */
public class SimpleCryptoTests extends AbstractTransactionalDataSourceSpringContextTests {

	public static String encrypt(String seed, String cleartext) throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = encrypt(rawKey, cleartext.getBytes("UTF8"));
		return toHex(result);
	}

	public static String decrypt(String seed, String encrypted) throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] enc = toByte(encrypted);
		byte[] result = decrypt(rawKey, enc);
		return new String(result, "UTF8");
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}
	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length()/2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2*buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";
	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
	}

    public void testSimpleCrypto() {
    	try {
    		String key = "some seed 0231";
			String clearText;
			String encryptText;
			String decryptText;

			clearText = "Encryption Test 1 - Customer John Smith";
			System.out.println("    Case 1 - encrypt to hex string");
			encryptText = encrypt(key, clearText);
			decryptText = decrypt(key, encryptText);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptText);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			clearText = "Encryption Test 2 - John Smith's Credit Card: 4321987634125556";
			System.out.println("    Case 2 - encrypt to hex string");
			encryptText = encrypt(key, clearText);
			decryptText = decrypt(key, encryptText);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptText);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			clearText = "Encryption Test 3 - John Smith's bank account: 121-546-3655502";
			System.out.println("    Case 3 - encrypt to hex string");
			encryptText = encrypt(key, clearText);
			decryptText = decrypt(key, encryptText);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptText);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			clearText = "Encryption Test 4 - random  t e  x   t 122 @#1 *^&1";
			System.out.println("    Case 4 - encrypt to hex string");
			encryptText = encrypt(key, clearText);
			decryptText = decrypt(key, encryptText);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptText);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
    		e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testSimpleCrypto2() {
		System.out.println("Test --> testSimpleCrypto2");
		try {
    		String key = "some seed 0231";
			String clearText1, clearText2, clearText3, clearText4;
			String encryptText1, encryptText2, encryptText3, encryptText4;
			String decryptText1, decryptText2, decryptText3, decryptText4;

			clearText1 = "Encryption Test 1 - Customer John Smith";
			clearText2 = "Encryption Test 2 - John Smith's Credit Card: 4321987634125556";
			clearText3 = "Encryption Test 3 - John Smith's bank account: 121-546-3655502";
			clearText4 = "Encryption Test 4 - random  t e  x   t 122 @#1 *^&1";
			System.out.println("    Encrypt to hex string");
			encryptText1 = encrypt(key, clearText1);
			encryptText2 = encrypt(key, clearText2);
			encryptText3 = encrypt(key, clearText3);
			encryptText4 = encrypt(key, clearText4);
			decryptText1 = decrypt(key, encryptText1);
			decryptText2 = decrypt(key, encryptText2);
			decryptText3 = decrypt(key, encryptText3);
			decryptText4 = decrypt(key, encryptText4);
			System.out.println("    Clear1  : " + clearText1);
			System.out.println("    Encrypt1: " + encryptText1);
			System.out.println("    Decrypt1: " + decryptText1);
			assertEquals(clearText1, decryptText1);
			System.out.println("    PASS");
			System.out.println("    Clear2  : " + clearText2);
			System.out.println("    Encrypt2: " + encryptText2);
			System.out.println("    Decrypt2: " + decryptText2);
			assertEquals(clearText2, decryptText2);
			System.out.println("    PASS");
			System.out.println("    PASS");
			System.out.println("    Clear3  : " + clearText3);
			System.out.println("    Encrypt3: " + encryptText3);
			System.out.println("    Decrypt3: " + decryptText3);
			assertEquals(clearText3, decryptText3);
			System.out.println("    PASS");
			System.out.println("    Clear4  : " + clearText4);
			System.out.println("    Encrypt4: " + encryptText4);
			System.out.println("    Decrypt4: " + decryptText4);
			assertEquals(clearText4, decryptText4);
			System.out.println("    PASS");

			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("Exception");
			fail(e.getMessage());
		}
	}

}
