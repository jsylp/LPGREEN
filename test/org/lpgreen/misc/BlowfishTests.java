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

	public void testBlowfishEncryption2() {
		System.out.println("Test --> testBlowfishEncryption2");
		try {
			Blowfish bfCipher = new Blowfish();
			String clearText1, clearText2, clearText3, clearText4;
			String encryptText1, encryptText2, encryptText3, encryptText4;
			String decryptText1, decryptText2, decryptText3, decryptText4;
			byte[] encryptBytes1, encryptBytes2, encryptBytes3, encryptBytes4;

			clearText1 = "Encryption Test 1 - Customer John Smith";
			clearText2 = "Encryption Test 2 - John Smith's Credit Card: 4321987634125556";
			clearText3 = "Encryption Test 3 - John Smith's bank account: 121-546-3655502";
			clearText4 = "Encryption Test 4 - random  t e  x   t 122 @#1 *^&1";
			System.out.println("    Encrypt to byte[]");
			encryptBytes1 = bfCipher.encryptToBytes(clearText1);
			encryptBytes2 = bfCipher.encryptToBytes(clearText2);
			encryptBytes3 = bfCipher.encryptToBytes(clearText3);
			encryptBytes4 = bfCipher.encryptToBytes(clearText4);
			decryptText1 = bfCipher.decryptFromBytes(encryptBytes1);
			decryptText2 = bfCipher.decryptFromBytes(encryptBytes2);
			decryptText3 = bfCipher.decryptFromBytes(encryptBytes3);
			decryptText4 = bfCipher.decryptFromBytes(encryptBytes4);
			System.out.println("    Clear1  : " + clearText1);
			System.out.println("    Encrypt1: " + encryptBytes1);
			System.out.println("    Decrypt1: " + decryptText1);
			assertEquals(clearText1, decryptText1);
			System.out.println("    PASS");
			System.out.println("    Clear2  : " + clearText2);
			System.out.println("    Encrypt2: " + encryptBytes2);
			System.out.println("    Decrypt2: " + decryptText2);
			assertEquals(clearText2, decryptText2);
			System.out.println("    PASS");
			System.out.println("    Clear3  : " + clearText3);
			System.out.println("    Encrypt3: " + encryptBytes3);
			System.out.println("    Decrypt3: " + decryptText3);
			assertEquals(clearText2, decryptText2);
			System.out.println("    PASS");
			System.out.println("    Clear4  : " + clearText4);
			System.out.println("    Encrypt4: " + encryptBytes4);
			System.out.println("    Decrypt4: " + decryptText4);
			assertEquals(clearText2, decryptText2);
			System.out.println("    PASS");

			System.out.println("    Encrypt to hex string");
			encryptText1 = bfCipher.encrypt(clearText1);
			encryptText2 = bfCipher.encrypt(clearText2);
			encryptText3 = bfCipher.encrypt(clearText3);
			encryptText4 = bfCipher.encrypt(clearText4);
			decryptText1 = bfCipher.decrypt(encryptText1);
			decryptText2 = bfCipher.decrypt(encryptText2);
			decryptText3 = bfCipher.decrypt(encryptText3);
			decryptText4 = bfCipher.decrypt(encryptText4);
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
