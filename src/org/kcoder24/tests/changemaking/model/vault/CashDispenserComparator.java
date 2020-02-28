package org.kcoder24.tests.changemaking.model.vault;

import java.util.Comparator;

import org.kcoder24.tests.changemaking.model.Dispenser;
import org.kcoder24.tests.changemaking.model.vault.Cash.CASH_TYPE;

public class CashDispenserComparator implements Comparator<Dispenser<Cash>> {

	@Override
	public int compare(Dispenser<Cash> o1, Dispenser<Cash> o2) {
		Cash t1 = o1.getModel();
		Cash t2 = o2.getModel();
		if (t1.getType() == CASH_TYPE.TICKET && t2.getType() == CASH_TYPE.COIN) {
			return -1;
		}else if (t1.getType() == CASH_TYPE.COIN && t2.getType() == CASH_TYPE.TICKET) {
			return 1;
		}else {
			return t2.getValue() - t1.getValue();
		}
	}

}
