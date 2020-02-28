package org.cang24.tests.operation;

import java.util.Map;

import org.cang24.tests.changemaking.model.Dispenser;
import org.cang24.tests.changemaking.model.dispenser.Product;
import org.cang24.tests.changemaking.model.vault.Cash;
import org.cang24.tests.changemaking.model.vault.Vault;
import org.cang24.tests.changemaking.ui.MenuManager;

public abstract class Operation {
	protected MenuManager menuMgr;
	
	protected Map<Product, Dispenser<Product>> productDispensers;
	protected Map<Cash, Dispenser<Cash>> coinDispensers;
	protected Map<Cash, Dispenser<Cash>> ticketDispensers;
	
	protected Vault vaultCash;
	
	public Operation(MenuManager menuMgr, Map<Product, Dispenser<Product>> productDispensers,
			Map<Cash, Dispenser<Cash>> coinDispensers, Map<Cash, Dispenser<Cash>> ticketDispensers, Vault vaultCash) {
		
		super();
		
		this.menuMgr = menuMgr;
		this.productDispensers = productDispensers;
		this.coinDispensers = coinDispensers;
		this.ticketDispensers = ticketDispensers;
		this.vaultCash = vaultCash;
	}

	public abstract void execute();
	
	protected Product getProductInDispenserById(int idProduct) {
		for(Product p : productDispensers.keySet()) {
			if (p.getId() == idProduct) {
				return p;
			}
		}
		return null;
	}
}
