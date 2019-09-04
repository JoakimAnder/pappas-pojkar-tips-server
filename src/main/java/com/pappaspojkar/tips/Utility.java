/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pappaspojkar.tips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneOffset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author mehtab
 */
public class Utility {

    @Autowired
    private static Environment env;

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final Integer SECONDS_UNTIL_AUTOMATIC_LOGOUT = 15*60;
    public static final Integer SECONDS_OF_LOGIN_DENIAL = 60*60;
    public static final ZoneOffset SERVER_OFFSET = ZoneOffset.UTC;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_FULL_NAME_REGEX =
            Pattern.compile("^[a-zA-z ]*$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PHONE_REGEX =
            Pattern.compile("^[0-9]{10,13}$", Pattern.CASE_INSENSITIVE);


    /** Generates a token, used to authenticate a login attempt.
     *
     * @return The generated token. Should be sent to DB and to the client.
     */
    public static String generateToken() {
        int count = 20;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /** Encodes a String using MD5 and some salt.
     *  Should be used on every password... EVER!
     *
     * @param password Input which should be encoded.
     * @return  Encoded String
     */
    public static String MD5Encode(String password) {
        try {
            byte[] bytes = MessageDigest.getInstance("MD5")
                    .digest(password.concat(env.getProperty("password.salt", "salt")).getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b: bytes) {
                sb.append(Integer.toString((b & 0xff)  + 0x100, 16));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    /** Checks if str is a full name e.g. no numbers or signs
     *
     * @param str String to be checked
     * @return true if valid else false
     */
    public static boolean isValidFullName(String str) {
        return VALID_FULL_NAME_REGEX.matcher(str).matches();
    }

    /** Checks if str is a email address e.g. 'x@y.z'
     *
     * @param str String to be checked
     * @return true if valid else false
     */
    public static boolean isValidEmail(String str) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(str);
        return matcher.matches();
    }

    /** Checks if str is 13-15 numbers
     *
     * @param str String to be checked
     * @return true if valid else false
     */
    public static boolean isValidPhone(String str) {
        Matcher matcher = VALID_PHONE_REGEX .matcher(str);
        return matcher.matches();
    }


}


