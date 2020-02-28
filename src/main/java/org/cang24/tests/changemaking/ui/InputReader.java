package org.cang24.tests.changemaking.ui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputReader {
	private static Scanner in = ScannerManager.getScanner();
	
	public static String readPatternString(String msg, String validationPattern, String extractionPattern) {
		
		while (true) {
			System.out.println(msg);
			String selection = in.next();
			selection = selection.trim();
			if (selection.matches(validationPattern)) {
				Pattern pattern = Pattern.compile(extractionPattern);
				Matcher matcher = pattern.matcher(selection);
				if (matcher.find()) {
					return matcher.group();
				}
				return null;
			}else {
				System.out.println("Entrada invalida");
			}
		}
	}
	
	public static int readPositiveNumber(String msg) {
		while (true) {
			System.out.println(msg);
			String selection = in.next();
			selection = selection.trim();
			if (validNumber(selection)) {
				return Integer.parseInt(selection);
			}else {
				System.out.println("Entrada invalida, ingrese numeros enteros positivos");
			}
		}
	}
	
	private static boolean validNumber(String selection) {
		// Validate that this is a number
		for (int i = 0; i<selection.length(); i++) {
			if (!Character.isDigit(selection.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}
}
