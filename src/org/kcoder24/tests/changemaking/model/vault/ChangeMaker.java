package org.kcoder24.tests.changemaking.model.vault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kcoder24.tests.changemaking.model.Dispenser;

public class ChangeMaker {
	Map<Cash, Dispenser<Cash>> ticketDispensers;
	Map<Cash, Dispenser<Cash>> coinDispensers;
	List<Dispenser<Cash>> dispensers;
	

	public ChangeMaker(Map<Cash, Dispenser<Cash>> ticketDispensers, Map<Cash, Dispenser<Cash>> coinDispensers) {
		this.ticketDispensers = ticketDispensers;
		this.coinDispensers = coinDispensers;
		
		this.dispensers = new ArrayList<Dispenser<Cash>>();
		
		for (Cash c : coinDispensers.keySet()) {
			this.dispensers.add(coinDispensers.get(c));
		}
		
		for (Cash t : ticketDispensers.keySet()) {
			this.dispensers.add(ticketDispensers.get(t));
		}
		
		this.dispensers.sort(new CashDispenserComparator());
		
		for (Dispenser<Cash> disp : dispensers) {
			System.out.println("    $" + disp.getModel().getValue() + ": " + disp.getCurrentAmount());
		}
	}
	
	public Map<Cash, Integer> makeChange(int cashInserted, int productCost) {
		int amount = cashInserted - productCost;
		Map change = new HashMap<Cash, Integer>();
		
		if (amount <= 0) {
			return null;
		}else {
			if (getCoins(dispensers, 0, amount, change)) {
				return change;
			}else {
				return null;
			}
		}
	}
	
	private boolean getCoins(List<Dispenser<Cash>> dispensers, int idxCurCoin, int amount, Map<Cash, Integer> change) {
		boolean result = false;
		if (amount > 0) {
			Dispenser<Cash> disp = dispensers.get(idxCurCoin);
			Cash cashToProcess = disp.getModel();
			int numCoins = amount / cashToProcess.getValue();
			//Adjust the numCoins if it is more than the available amount of coins
			numCoins = (numCoins > disp.getCurrentAmount()?disp.getCurrentAmount():numCoins);
			int residual = amount - (numCoins * cashToProcess.getValue());
			
			if (residual > 0) {
				if (idxCurCoin < dispensers.size() - 1) {
					for(int i = numCoins; i>=0 && result == false; i--) {
						// Recalculate the residual and the result for this level
						residual = amount - (i * cashToProcess.getValue());
						
						boolean cascadeChangeOK = getCoins(dispensers, idxCurCoin + 1, residual, change);
						if (cascadeChangeOK) {
							if (i>0) {
								change.put(cashToProcess, i);
							}
							result = true;
							return result; 
						}
					}
					return result;
				}else {
					return result;
				}
			}else {
				if (numCoins>0) {
					change.put(cashToProcess, numCoins);
					result = true;
				}
				return result;
			}
		}else {
			return result;
		}
	}
	
}
