package org.lpgreen.web;

import org.lpgreen.misc.LPCipher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * It is a controller class for Department related actions
 * 
 * Creation date: Jan. 18, 2013
 * Last modify date: Jan. 18, 2013
 * 
 * @author  J. Stephen Yu
 * @version 1.0
 */
@Controller
@RequestMapping("/security/*")
public class SecurityController {
	/**
	 * Encryption and decryption test action
	 * 
	 * @param msg: raw message
	 * @param model: holds the encrypted and decrypted msgs
	 * @return Success or error view
	 */
	@RequestMapping(value = "encryptmsg", method = RequestMethod.POST)
	public String encryptMessage(
			@RequestParam("rawmsg") String rawmsg,
			Model model) {
			
		final String funcName = "SecurityController.encryptMessage";

		if (rawmsg != null && !rawmsg.isEmpty()) {
			try {
				String encryptedmsg = null;
				String decryptedmsg = null;
				
				LPCipher lpCipher = new LPCipher();
				encryptedmsg = lpCipher.encrypt(rawmsg);
				decryptedmsg = lpCipher.decrypt(encryptedmsg);
				
				model.addAttribute("encryptedmsg", encryptedmsg);
				model.addAttribute("decryptedmsg", decryptedmsg);
			}
			catch (Exception e) {
				System.out.println(funcName + " Exception: " + e.getMessage());
			}
		}
		model.addAttribute("rawmsg", rawmsg);
		return "testencryption_result";
	}
	

}
