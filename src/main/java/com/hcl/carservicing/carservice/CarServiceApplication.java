package com.hcl.carservicing.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CarServiceApplication {

	public static void main(String[] args) {
//		SpringApplication.run(CarServiceApplication.class, args);
		someMethod();
	}

	private static void someMethod() {
		new RuntimeException();
		String str = "manikanta";

		char[] charArray = str.toCharArray();

		int n = charArray.length;
		for(int i= 0;i<n/2;i++) {
			char temp = charArray[i];
			charArray[i] = charArray[n-i-1];
			charArray[n-i-1] = temp;
		}
//		String revstring = Arrays.toString(charArray);
		String revstring = new String(charArray);

		System.out.println(str);
		System.out.println(revstring);
	}

}
