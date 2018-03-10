package cn.imaq.trainingcollege.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static String hash(String src) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return byte2Hex(messageDigest.digest(src.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                sb.append('0');
            }
            sb.append(temp);
        }
        return sb.toString();
    }
}
