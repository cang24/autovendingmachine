package org.kcoder24.tests.operation;

import java.util.Map;

import org.kcoder24.tests.changemaking.model.Dispenser;
import org.kcoder24.tests.changemaking.model.dispenser.Product;
import org.kcoder24.tests.changemaking.model.vault.Cash;
import org.kcoder24.tests.changemaking.model.vault.Vault;
import org.kcoder24.tests.changemaking.ui.InputReader;
import org.kcoder24.tests.changemaking.ui.MenuManager;

public class ProductRefillOperation extends Operation {

	public ProductRefillOperation(MenuManager menuMgr, Map<Product, Dispenser<Product>> productDispensers,
			Map<Cash, Dispenser<Cash>> coinDispensers, Map<Cash, Dispenser<Cash>> ticketDispensers, Vault vaultCash) {
		super(menuMgr, productDispensers, coinDispensers, ticketDispensers, vaultCash);
	}

	@Override
	public void execute() {
		System.out.println("Ejecutando ProductRefill");
		int selProduct = menuMgr.showRefillProductMenu();
		
		if (selProduct != 2406) {
			Product selectedProduct = getProductInDispenserById(selProduct);
			Dispenser<Product> dispenser = productDispensers.get(selectedProduct);
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

}
