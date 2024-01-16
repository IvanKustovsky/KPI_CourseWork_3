package com.example.coursework.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {
    private static final String SALT = "someRandomSalt"; // add your own salt here

    public static String encrypt(String password) {
        String saltedPassword = SALT + password;
        return hash(saltedPassword);
    }

    public static boolean verify(String password, String hashedPassword) {
        String saltedPassword = SALT + password;
        String hashedPasswordToVerify = hash(saltedPassword);
        return hashedPasswordToVerify.equals(hashedPassword);
    }

    private static String hash(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(input.getBytes());
            byte[] digest = messageDigest.digest();
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
