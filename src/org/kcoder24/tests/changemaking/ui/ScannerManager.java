package org.kcoder24.tests.changemaking.ui;

import java.util.Scanner;

public class ScannerManager {
	private static Scanner scanner;
	
	static {
		scanner = new Scanner(System.in);
	}
	
	public static Scanner getScanner() {
		return scanner;
	}
}
