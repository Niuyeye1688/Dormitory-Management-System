package com.dormitory.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {

    private static final String SHA256_PREFIX = "SHA256:";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String encrypt(String input) {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        String saltHex = bytesToHex(salt);
        String hash = sha256Hex(input + saltHex);
        return SHA256_PREFIX + saltHex + ":" + hash;
    }

    public static boolean verify(String input, String storedPassword) {
        if (storedPassword == null || storedPassword.isEmpty()) {
            return false;
        }
        if (storedPassword.startsWith(SHA256_PREFIX)) {
            return verifySha256(input, storedPassword);
        }
        return verifyMD5(input, storedPassword);
    }

    private static boolean verifySha256(String input, String storedPassword) {
        String withoutPrefix = storedPassword.substring(SHA256_PREFIX.length());
        int splitIdx = withoutPrefix.indexOf(':');
        if (splitIdx < 0) return false;
        String salt = withoutPrefix.substring(0, splitIdx);
        String hash = withoutPrefix.substring(splitIdx + 1);
        return sha256Hex(input + salt).equals(hash);
    }

    private static boolean verifyMD5(String input, String storedPassword) {
        return md5Hex(input).equals(storedPassword);
    }

    private static String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes());
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static String md5Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(input.getBytes());
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not available", e);
        }
    }
}
