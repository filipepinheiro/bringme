package pt.ua.icm.bringme;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 
 * @author Filipe Pinheiro
 * Simple password security using MD5 algorithm.
 * 
 */

public class Digest {
	private static String generatedPassword = null;
	
	/**
	 * @author Lokesh Gupta
	 * Found in <a href="http://howtodoinjava.com/2013/07/22/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/">here</a>
	 * @param passwordToHash
	 * @return Md5Password
	 */
	public static String md5(String passwordToHash){
		try {
	        // Create MessageDigest instance for MD5
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        //Add password bytes to digest
	        md.update(passwordToHash.getBytes());
	        //Get the hash's bytes
	        byte[] bytes = md.digest();
	        //This bytes[] has bytes in decimal format;
	        //Convert it to hexadecimal format
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++)
	        {
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        //Get complete hashed password in hex format
	        generatedPassword = sb.toString();
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
		
		return generatedPassword;
	}

}
