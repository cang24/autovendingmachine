package org.kcoder24.tests.changemaking.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kcoder24.tests.changemaking.model.dispenser.Product;
import org.kcoder24.tests.changemaking.model.vault.Cash;
import org.kcoder24.tests.changemaking.model.vault.Cash.CASH_TYPE;
import org.kcoder24.tests.changemaking.model.vault.ChangeMaker;
import org.kcoder24.tests.changemaking.model.vault.Vault;
import org.kcoder24.tests.changemaking.ui.InputReader;
import org.kcoder24.tests.changemaking.ui.MenuManager;
import org.kcoder24.tests.operation.ProductRefillOperation;
import org.kcoder24.tests.operation.SellOperation;

public class AutoVendingMachine {
	List<Product> productList;
	List<Cash> coinList;
	List<Cash> ticketList;
	
	Map<Product, Dispenser<Product>> productDispensers;
	Map<Cash, Dispenser<Cash>> coinDispensers;
	Map<Cash, Dispenser<Cash>> ticketDispensers;
	
	MenuManager menuMgr;
	
	Vault vaultCash;
	
	public AutoVendingMachine() {
		initEnvironment();
		
		createDispensers();
		
		menuMgr = new MenuManager(productList, coinList, ticketList);
		vaultCash = new Vault(0);
	}
	
	public void run() {
		boolean finished = false;
		
		while (!finished) {
			printCurrentState();
			
			int selOperation = menuMgr.showMainMenu();
			
			switch(selOperation) {
			case 1:
				executeSell();
				break;
			case 2:
				executeProductRefill();
				break;
			case 3:
				executeCashRefill();
				break;
			case 4:
				executeWithdrawal();
				break;
			case 2406:
				finished = true;
				break;
			default:
				System.out.println("Chetoos, un punto menos");
			}
		}
	}

	private void executeWithdrawal() {
		System.out.println("Ejecutando Withdrawal");
		int amountToGet = InputReader.readPositiveNumber(
				"Introduzca importe a retirar(0 - nada y terminar): ");
		
		if (amountToGet > 0) {
			if (amountToGet <= vaultCash.getBalance()) {
				vaultCash.getCash(amountToGet);
				System.out.println("Por favor retire el dinero");
			}else {
				System.out.println("No se cuenta con suficiente cantidad");
			}
		}
	}

	private void executeCashRefill() {
		Map<Cash, Dispenser<Cash>> dispensers;
		int idDenomSelected;
		System.out.println("Ejecutando CashRefill");
		int selOperation = menuMgr.showCashToRefillMenu();
		
		switch(selOperation) {
		case 1:
			dispensers = coinDispensers;
			idDenomSelected = menuMgr.showCoinsMenu();
			break;
		case 2:
			dispensers = ticketDispensers;
			idDenomSelected = menuMgr.showTicketsMenu();
			break;
		case 2406:
			return;
		default:
			System.out.println("Chetoos, un punto menos");
			return;
		}
		
		if (idDenomSelected != 2406) {
			Cash selectedCash = getCashInDispenserById(dispensers, idDenomSelected);
			Dispenser<Cash> dispenser = dispensers.get(selectedCash);
			if (dispenser == null) {
				System.out.println("Ouch, va a doler, un punto menos");
			}else {
				
				int amountToRefill = InputReader.readPositiveNumber(
						"Introduzca la cantidad de elementos a rellenar (0 - nada y continuar): ");
				
				if (amountToRefill == 0) {
					System.out.println("No se agregan elementos para rellenar");
				}else {
					if (dispenser.canAcceptNItems(amountToRefill)) {
						dispenser.putItems(amountToRefill);
					}else {
						System.out.println("La cantidad excede el limite(" + dispenser.getMaxLimit() + 
								" elementos), cancelando operacion");
					}
					System.out.println("Actualmente este cartucho tiene " + dispenser.getCurrentAmount() + 
							"/" + dispenser.getMaxLimit() + " elementos");
				}
			}
		}
	}

	private Cash getCashInDispenserById(Map<Cash, Dispenser<Cash>> dispensers, int idDenomSelected) {
		for(Cash d : dispensers.keySet()) {
			if (d.getId() == idDenomSelected) {
				return d;
			}
		}
		return null;
	}
	
	private void executeProductRefill() {
		ProductRefillOperation prodRefillOperation = new ProductRefillOperation(menuMgr, productDispensers, coinDispensers, ticketDispensers, vaultCash);
		prodRefillOperation.execute();
	}

	private void executeSell() {
		SellOperation sellOperation = new SellOperation(menuMgr, productDispensers, coinDispensers, ticketDispensers, vaultCash);
		sellOperation.execute();
	}

	private void createDispensers() {
		productDispensers = new HashMap<Product, Dispenser<Product>>();
		coinDispensers = new HashMap<Cash, Dispenser<Cash>>();
		ticketDispensers = new HashMap<Cash, Dispenser<Cash>>();
		
		for (Product p : productList) {
			productDispensers.put(p, new Dispenser<Product>(p, 10));
		}
		
		for (Cash c : coinList) {
			coinDispensers.put(c, new Dispenser<Cash>(c, getLimitForCash(c)));
		}
		
		for (Cash t : ticketList) {
			ticketDispensers.put(t, new Dispenser<Cash>(t, getLimitForCash(t)));
		}
	}
	
	private int getLimitForCash(Cash cash) {
		switch(cash.getType()) {
		case COIN:
			switch (cash.getValue()) {
			case 1:return 100;
			case 5:return 50;
			case 10:return 20;
			}
			break;
		case TICKET:
			return 20;
		}
		
		return 0;
	}

	private void initEnvironment() {
		productList = new ArrayList<Product>();
		productList.add(new Product(12, "Coca Cola"));
		productList.add(new Product(17, "Fanta"));
		productList.add(new Product(23, "Sprite"));
		
		coinList = new ArrayList<Cash>();
		coinList.add(new Cash(Cash.CASH_TYPE.COIN, 1));
		coinList.add(new Cash(Cash.CASH_TYPE.COIN, 5));
		coinList.add(new Cash(Cash.CASH_TYPE.COIN, 10));
		
		ticketList = new ArrayList<Cash>();
		ticketList.add(new Cash(Cash.CASH_TYPE.TICKET, 20));
		ticketList.add(new Cash(Cash.CASH_TYPE.TICKET, 50));
		ticketList.add(new Cash(Cash.CASH_TYPE.TICKET, 100));
		ticketList.add(new Cash(Cash.CASH_TYPE.TICKET, 200));
	}
	
	void printCurrentState() {
		System.out.println("=====================================================================================");
		System.out.println("Contenido de dispensadores de monedas:");
		for (Cash c : coinDispensers.keySet()) {
			System.out.println("    $" + c.getValue() + ": " + coinDispensers.get(c).getCurrentAmount() + "/" +
					coinDispensers.get(c).getMaxLimit());
		}
		System.out.println("Contenido de dispensadores de billetes:");
		for (Cash c : ticketDispensers.keySet()) {
			System.out.println("    $" + c.getValue() + ": " + ticketDispensers.get(c).getCurrentAmount() + "/" +
					ticketDispensers.get(c).getMaxLimit());
		}
		System.out.println("Contenido de dispensadores de producto:");
		for (Product p : productDispensers.keySet()) {
			System.out.println("    " + p.getDescription() + ": " + productDispensers.get(p).getCurrentAmount() + "/" +
					productDispensers.get(p).getMaxLimit());
		}
		System.out.println("Dinero en alcancia: " + vaultCash.getBalance());
		System.out.println("-------------------------------------------------------------------------------------");
	}
}
