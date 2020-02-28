package org.kcoder24.tests.changemaking.ui;

import java.util.List;
import java.util.Scanner;

public class Menu {
	public final static int EXIT_SELECTED = 2406;
	
	List <MenuOption> options;
	String msg;
	boolean showExitOption;
	
	protected Menu(String msg, List <MenuOption> options, boolean showExitOption) {
		this.msg = msg;
		this.options = options;
		this.showExitOption = showExitOption;
	}
	
	private void show() {
		System.out.println(msg);
		
		StringBuilder sb = new StringBuilder("");
		for(MenuOption curOption : options) {
			sb.append("    ").append(curOption.getIndex()).append(".- ").append(curOption.getDescription()).append("\n");
		}
		
		if (showExitOption) {
			sb.append("    S.- Salir");
		}
		System.out.println(sb.toString());
	}
	
	private int readInput() {
		Scanner in = ScannerManager.getScanner();
		
		while (true) {
			show();
			String selection = in.next();
			if (validNumber(selection)) {
				return Integer.parseInt(selection);
			}else if (selection.toUpperCase().equals("S")) {
				return EXIT_SELECTED;
			}else {
				System.out.println("Opcion invalida, por favor intente nuevamente.");
			}
		}
	}
	
	private boolean validNumber(String selection) {
		// Validate that this is a number
		for (int i = 0; i<selection.length(); i++) {
			if (!Character.isDigit(selection.charAt(i))) {
				return false;
			}
		}
		// Validate the number is a valid selection
		int numSelection = Integer.parseInt(selection);
		boolean found = false;
		for(MenuOption curOption : options) {
			if (numSelection == curOption.getIndex()) {
				found = true;
			}
		}
		
		return found;
	}

	public int run() {
		return readInput();
	}
}
