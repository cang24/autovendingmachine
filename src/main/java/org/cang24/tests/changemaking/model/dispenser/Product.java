package org.cang24.tests.changemaking.model.dispenser;

import org.cang24.tests.changemaking.model.ItemValue;

public class Product implements ItemValue{
	private static int idCounter;
	int value;
	String description;
	int id;
	
	public Product(int value, String description) {
		super();
		this.value = value;
		this.description = description;
		this.id = ++ idCounter;
	}
	public int getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
	public int getId() {
		return id;
	}
}
