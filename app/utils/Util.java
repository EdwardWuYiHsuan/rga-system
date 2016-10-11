package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

import play.Logger;

public class Util {
	
	private static String VALID_EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.length() == 0 || s.equals("null");
	}
	
	public static boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(VALID_EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static String sha256InBase64(String text) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            Base64 base64 = new Base64(-1);
            hash = base64.encodeToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            Logger.error("Unable to generate SHA-256 hash.", e);
        } catch (UnsupportedEncodingException e) {
            Logger.error("Unable to generate SHA-256 hash.", e);
        }
        return hash;
    }
}
