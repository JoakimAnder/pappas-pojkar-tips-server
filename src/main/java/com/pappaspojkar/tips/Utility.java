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

import org.apache.tomcat.util.security.MD5Encoder;

/**
 *
 * @author mehtab
 */
public class Utility {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final Integer SECONDS_UNTIL_AUTOMATIC_LOGOUT = 15*60;
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
            return new String(MessageDigest.getInstance("MD5")
                .digest(password.concat("test")
                .getBytes(Charset.forName("UTF-8")))); //TODO fix salt Enviroment variable
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
		} 
    }


}
