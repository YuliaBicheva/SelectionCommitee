package ua.nure.bycheva.SummaryTask4.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * Created by yulia on 03.09.16.
 */
public abstract class PasswordUtil {

    private static final Logger LOG = Logger.getLogger(PasswordUtil.class);

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static String hash(String str) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest digest;
        StringBuffer hexString = new StringBuffer();
        digest = MessageDigest.getInstance("SHA-512");
        digest.update(str.getBytes("UTF-8"));
        for (byte d : digest.digest()) {
            hexString.append(getFirstHexDigit(d)).append(getSecondHexDigit(d));
        }
        return hexString.toString();
    }

    private static char getFirstHexDigit(byte x) {
        return HEX_DIGITS[(0xFF & x) / 16];
    }

    private static char getSecondHexDigit(byte x) {
        return HEX_DIGITS[(0xFF & x) % 16];
    }

    public static boolean verifyPass(String expected, String actual) {
        LOG.debug("Start verifications passwords");
        try {
            return hash(actual).equals(expected);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOG.error(e.getMessage());
            return false;
        }
    }
}
