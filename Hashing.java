import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hashing {
	public static void main(String[] args) {
		String algorithm = "SHA-256";
		String passwd = "admin123";
		String result = hashing(algorithm, passwd);
		
		System.out.println("res : " + result);
	}
	
	private static String hashing(String algorithm, String strPlaintext) {
		String strHashedData = "";
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			System.out.println(strPlaintext);
			
			byte[] byteText = strPlaintext.getBytes();
			System.out.println(Arrays.toString(byteText));
			
			md.update(byteText);
	
			byte[] digest = md.digest();
			
//			System.out.println(Arrays.toString(digest));
			
			for(byte b : digest) {
				strHashedData += Integer.toHexString(b & 0xFF).toUpperCase();
				
			}
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println("입력한 암호화 알고리즘 존재 x");
			e.printStackTrace();
		}
		return strHashedData;
	}
}
