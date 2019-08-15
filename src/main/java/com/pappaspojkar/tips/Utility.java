/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pappaspojkar.tips;

import org.apache.tomcat.util.security.MD5Encoder;

import sun.security.provider.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author mehtab
 */
public class Utility {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static void main(String[] args) {

        System.out.println(encodeMD5("hej"));


    }



    
public static String generateToken() {
    int count = 20;
StringBuilder builder = new StringBuilder();
while (count-- != 0) {
int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
builder.append(ALPHA_NUMERIC_STRING.charAt(character));
}
return builder.toString();
}

    public static String encodeMD5(String password)  {
        MessageDigest md = null;
        try {
            // Create MessageDigest instance for MD5
            md = MessageDigest.getInstance("MD5");


            //Get the hash's bytes
            byte [] bytes = md.digest(password.concat("salt").getBytes());

            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            password = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return password;
    }


}


