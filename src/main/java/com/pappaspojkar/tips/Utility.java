/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pappaspojkar.tips;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author mehtab
 */
public class Utility {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final Integer SECONDS_UNTIL_AUTOMATIC_LOGOUT = 15*60;
    public static final Integer SECONDS_OF_LOGIN_DENIAL = 60*60;
    public static final ZoneOffset SERVER_OFFSET = ZoneOffset.UTC;
    

    public static String generateToken() {
        int count = 20;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String MD5Encode(String password) {
        try {
            byte[] bytes = MessageDigest.getInstance("MD5")
                .digest(password.concat("salt").getBytes()); //TODO fix salt Enviroment variable

            StringBuilder sb = new StringBuilder();
            for (byte b: bytes) {
                sb.append(Integer.toString((b & 0xff)  + 0x100, 16));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
		} 
    }


}
