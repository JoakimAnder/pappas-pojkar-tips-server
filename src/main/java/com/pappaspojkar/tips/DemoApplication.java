package com.pappaspojkar.tips;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Running...");
		
	}

}
