package org.cang24.tests.changemaking.ui;

import java.util.ArrayList;
import java.util.List;

import org.cang24.tests.changemaking.model.dispenser.Product;
import org.cang24.tests.changemaking.model.vault.Cash;

public class MenuManager {
	Menu mainMenu;
	Menu beveragesMenu;
	Menu cashToRefillMenu;
	Menu coinsMenu;
	Menu ticketsMenu;
	Menu refillProductMenu;
	
	public MenuManager(List<Product> productList, List<Cash> coinList, List<Cash> ticketList) {
		List <MenuOption> options;
		//Main Menu
		options = new ArrayList<MenuOption>();
		options.add(new MenuOption(1, "Comprar bebida"));
		options.add(new MenuOption(2, "Surtir producto"));
		options.add(new MenuOption(3, "Surtir cambio"));
		options.add(new MenuOption(4, "Retirar dinero"));
		mainMenu = new Menu("Seleccione la operacion a ejecutar:", options, true);
		
		//=========================================================================================
		//                               B u y   a   b e v e r a g e
		//=========================================================================================
		//Beverage options
		options = new ArrayList<MenuOption>();
		for (Product p: productList) {
			options.add(new MenuOption(p.getId(), p.getDescription()));
		}
		beveragesMenu = new Menu("Seleccione su bebida:", options, true);
		
		//=========================================================================================
		//                               R e f i l l   c a s h
		//=========================================================================================
		//Coin cartridge to refill options
		options = new ArrayList<MenuOption>();
		options.add(new MenuOption(1, "Cartucho de Monedas"));
		options.add(new MenuOption(2, "Cartucho de Billetes"));
		cashToRefillMenu = new Menu("Seleccione el tipo de cartucho a recargar:", options, true);
				
		//Coin cartridge to refill options
		options = new ArrayList<MenuOption>();
		for (Cash c: coinList) {
			options.add(new MenuOption(c.getId(), "Moneda de " + Integer.toString((c.getValue()))));
		}
		coinsMenu = new Menu("Seleccione cartucho de monedas a recargar:", options, true);
		
		//Ticket cartridge to refill options
		options = new ArrayList<MenuOption>();
		for (Cash t: ticketList) {
			options.add(new MenuOption(t.getId(), "Billete de $" + Integer.toString((t.getValue()))));
		}
		ticketsMenu = new Menu("Seleccione cartucho de billetes a recargar:", options, true);
		
		//=========================================================================================
		//                               P r o d u c t   c a s h
		//=========================================================================================
		//Product cartridge to refill options
		options = new ArrayList<MenuOption>();
		for (Product p: productList) {
			options.add(new MenuOption(p.getId(), p.getDescription()));
		}
		refillProductMenu = new Menu("Seleccione cartucho de producto a recargar:", options, true);
	}

	public int showMainMenu() {
		return mainMenu.run();
	}
	
	public int showCashToRefillMenu() {
		return cashToRefillMenu.run();
	}
	
	public int showCoinsMenu() {
		return coinsMenu.run();
	}
	
	public int showTicketsMenu() {
		return ticketsMenu.run();
	}

	public int showRefillProductMenu() {
		return refillProductMenu.run();
	}
	
	public int showBeveragesMenu() {
		return beveragesMenu.run();
	}
}

