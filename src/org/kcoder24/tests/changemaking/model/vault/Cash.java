package org.kcoder24.tests.changemaking.model.vault;

import org.kcoder24.tests.changemaking.model.ItemValue;

public class Cash implements ItemValue {
	public enum CASH_TYPE{
		COIN,
		TICKET
	}
	
	private static int idCounters[] = new int[2];
	
	private CASH_TYPE type;
	private int value;
	private int id;
	
	public Cash(CASH_TYPE type, int value) {
		super();
		this.type = type;
		this.value = value;
		this.id = ++idCounters[type == CASH_TYPE.COIN ? 0:1];
	}
	
	public int getValue() {
		return value;
	}
	
	public CASH_TYPE getType() {
		return type;
	}
	public int getId() {
		return id;
	}
}
