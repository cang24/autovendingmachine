package org.cang24.tests.changemaking.model.vault;

public class Vault {
	int amount;
	
	public Vault(Integer amount) {
		super();
		this.amount = amount;
	}
	
	public int getCash(int amount) {
		if (amount > this.amount) {
			System.out.println(":'(    Otro mas que no debe aparecer");
			return -1;
		}else {
			this.amount -= amount;
			return amount;
		}
	}
	public void putCash(int amount) {
		this.amount += amount;
	}
	public int getBalance() {
		return this.amount;
	}
}
