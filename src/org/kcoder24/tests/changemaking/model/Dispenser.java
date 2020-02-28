package org.kcoder24.tests.changemaking.model;

public class Dispenser<T extends ItemValue> {
	private T model;
	private int currentAmount;
	private int maxLimit;
	
	public Dispenser(T model, int maxLimit) {
		super();
		this.model = model;
		this.currentAmount = 0;
		this.maxLimit = maxLimit;
	}
	
	public int getValue() {
		return model.getValue();
	}

	public int getCurrentAmount() {
		return currentAmount;
	}
	
	public T getModel() {
		return this.model;
	}
	
	public boolean canProvideNItems(int amount) {
		return currentAmount >= amount;
	}
	
	public boolean canAcceptNItems(int amount) {
		return currentAmount + amount <= maxLimit;
	}
	
	public int getMaxLimit() {
		return this.maxLimit;
	}
	
	public int getItems(int amount) {
		if (canProvideNItems(amount)) {
			currentAmount -= amount;
			return amount;
		}else {
			return 0;
		}
	}
	
	public int putItems(int amount) {
		if (canAcceptNItems(amount)) {
			currentAmount += amount;
			return amount;
		}else {
			return 0;
		}
	}
}
