package org.cang24.tests.operation;

import java.util.Map;

import org.cang24.tests.changemaking.model.Dispenser;
import org.cang24.tests.changemaking.model.dispenser.Product;
import org.cang24.tests.changemaking.model.vault.Cash;
import org.cang24.tests.changemaking.model.vault.ChangeMaker;
import org.cang24.tests.changemaking.model.vault.Vault;
import org.cang24.tests.changemaking.model.vault.Cash.CASH_TYPE;
import org.cang24.tests.changemaking.ui.InputReader;
import org.cang24.tests.changemaking.ui.MenuManager;

public class SellOperation extends Operation{

	public SellOperation(MenuManager menuMgr, Map<Product, Dispenser<Product>> productDispensers,
			Map<Cash, Dispenser<Cash>> coinDispensers, Map<Cash, Dispenser<Cash>> ticketDispensers, Vault vaultCash) {
		super(menuMgr, productDispensers, coinDispensers, ticketDispensers, vaultCash);
	}

	@Override
	public void execute() {
		System.out.println("Ejecutando venta");
		
		int receivedCash = 0;
		
		while (true) {
			// Get the cash from the user
			receivedCash += InputReader.readPositiveNumber(
					"Introduzca importe (0 - nada y terminar): ");
			
			if (receivedCash > 0) {
				if (selectBeverage(receivedCash)) {
					//System.out.println("[DEBUG] execute -> Total en alcancia: " + vaultCash);
					return;
				}
			}else {
				System.out.println("Sin importe para comprar");
				return;
			}
		}
	}

	private boolean selectBeverage(int receivedCash) {
		while (true) {
			int selBeverage = menuMgr.showBeveragesMenu();
			
			if (selBeverage != 2406) {
				Product selectedProduct = super.getProductInDispenserById(selBeverage);
				Dispenser<Product> dispenser = productDispensers.get(selectedProduct);
				if (dispenser == null) {
					System.out.println("Ouch, va a doler, un punto menos");
				}else {
					if (dispenser.getCurrentAmount() > 0) {
						if (canSell(receivedCash, selectedProduct, dispenser)) {
							//System.out.println("[DEBUG] selectBeverage -> Total en alcancia: " + vaultCash);
							return true;
						}else {
							return false;
						}
					}else {
						System.out.println("La bebida seleccionada se ha agotado");
					}
				}
			}else {
				System.out.println("No ha seleccionado bebida, por favor tome su dinero");
				return true;
			}
		}//while to select beverage
	}

	private boolean canSell(int receivedCash, Product selectedProduct, Dispenser<Product> dispenser) {
		//Validate inserted cash is greater or equal to the cost
		if (receivedCash>=selectedProduct.getValue()) {
			if (receivedCash>selectedProduct.getValue()) {
				ChangeMaker cmaker = new ChangeMaker(ticketDispensers, coinDispensers);
				Map<Cash, Integer> change = cmaker.makeChange(receivedCash, selectedProduct.getValue());
				if (change == null) {
					System.out.println("Lo siento no cuento con el cambio necesario para atenderte");
					System.out.println("Por favor toma tu dinero");
				}else {
					acumulateSell(receivedCash, selectedProduct, dispenser);
					//System.out.println("[DEBUG] canSell -> Total en alcancia: " + vaultCash);
					System.out.println("... y su cambio:");
					for (Cash c : change.keySet()) {
						if (c.getType()==CASH_TYPE.COIN) {
							coinDispensers.get(c).getItems(change.get(c));
						}else {
							ticketDispensers.get(c).getItems(change.get(c));
						}
						System.out.println("    " + (c.getType() == CASH_TYPE.TICKET ? "Billete(s) de $" : "Moneda(s) de $") +
								c.getValue() + "---> " + change.get(c));
					}
				}
			}else {
				acumulateSell(receivedCash, selectedProduct, dispenser);
				System.out.println("Por favor tome su bebida");
			}
			
			return true;
		}else {
			System.out.println("La cantidad de dinero introducida ($" + receivedCash + 
					") es menor al costo del producto($" + selectedProduct.getValue() + ")");
			String ansRetry = InputReader.readPatternString(
					"Desea introducir mas dinero [s/n]?", "^[ ]*[SsNn][ ]*$", "[SsNn]");
			if (ansRetry != null) {
				ansRetry = ansRetry.toUpperCase();
				if (ansRetry.equals("N")) {
					System.out.println("Por favor toma tu dinero");
					return true;
				}else {
					return false;
				}
			}else {
				System.out.println("Ouch, otro que no debio pasar");
				return true;
			}
		}
	}

	private void acumulateSell(int receivedCash, Product selectedProduct, Dispenser<Product> dispenser) {
		vaultCash.putCash(receivedCash);
		System.out.println("Por favor tome su bebida");
		productDispensers.get(selectedProduct).getItems(1);
	}
}
