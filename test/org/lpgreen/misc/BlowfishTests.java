package org.lpgreen.misc;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the SecurityManager junit testing class. 
 * 
 * Creation date: Feb. 23, 2013
 * Last modify date: Feb. 23, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class BlowfishTests extends AbstractTransactionalDataSourceSpringContextTests {

	public void testBlowfishEncryption() {
		System.out.println("Test --> testBlowfishEncryption");
		try {
			Blowfish bfCipher = new Blowfish();
			String clearText;
			String encryptText;
			String decryptText;
			byte[] encryptBytes;

			clearText = "Encryption Test 1 - Customer John Smith";
			System.out.println("    Case 1 - encrypt to byte[]");
			encryptBytes = bfCipher.encryptToBytes(clearText);
			decryptText = bfCipher.decryptFromBytes(encryptBytes);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptBytes);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			System.out.println("    Case 1 - encrypt to hex string");
			encryptText = bfCipher.encrypt(clearText);
			decryptText = bfCipher.decrypt(encryptText);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptText);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			clearText = "Encryption Test 2 - John Smith's Credit Card: 4321987634125556";
			System.out.println("    Case 2 - encrypt to byte[]");
			encryptBytes = bfCipher.encryptToBytes(clearText);
			decryptText = bfCipher.decryptFromBytes(encryptBytes);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptBytes);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			System.out.println("    Case 2 - encrypt to hex string");
			encryptText = bfCipher.encrypt(clearText);
			decryptText = bfCipher.decrypt(encryptText);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptText);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			clearText = "Encryption Test 3 - John Smith's bank account: 121-546-3655502";
			System.out.println("    Case 3 - encrypt to byte[]");
			encryptBytes = bfCipher.encryptToBytes(clearText);
			decryptText = bfCipher.decryptFromBytes(encryptBytes);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptBytes);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			System.out.println("    Case 3 - encrypt to hex string");
			encryptText = bfCipher.encrypt(clearText);
			decryptText = bfCipher.decrypt(encryptText);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptText);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			clearText = "Encryption Test 4 - random  t e  x   t 122 @#1 *^&1";
			System.out.println("    Case 4 - encrypt to byte[]");
			encryptBytes = bfCipher.encryptToBytes(clearText);
			decryptText = bfCipher.decryptFromBytes(encryptBytes);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptBytes);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			System.out.println("    Case 4 - encrypt to hex string");
			encryptText = bfCipher.encrypt(clearText);
			decryptText = bfCipher.decrypt(encryptText);
			System.out.println("    Clear  : " + clearText);
			System.out.println("    Encrypt: " + encryptText);
			System.out.println("    Decrypt: " + decryptText);
			assertEquals(clearText, decryptText);
			System.out.println("    PASS");

			System.out.println("     <-- Done.");
		}
		catch (Exception e) {
			System.out.println("Exception");
			fail(e.getMessage());
		}
	}

}
