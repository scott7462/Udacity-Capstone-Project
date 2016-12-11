package scott.com.workhard.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by androiddev3 on 10/6/16.
 */

public class Md5 {
    private static final String MD5 = "MD5";

    public static String generateMD5(String text) {

        final MessageDigest messageDigest;
        byte[] digest;
        BigInteger bigInt = null;
        try {
            messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.reset();
            messageDigest.update(text.getBytes());
            digest = messageDigest.digest();
            bigInt = new BigInteger(1, digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        StringBuilder hashText = new StringBuilder(bigInt.toString(16));
        while (hashText.length() < 32) {
            hashText.append("0").append(hashText);
        }
        return hashText.toString();
    }
}
