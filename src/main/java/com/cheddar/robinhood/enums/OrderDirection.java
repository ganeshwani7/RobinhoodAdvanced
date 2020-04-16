package com.cheddar.robinhood.enums;

public enum OrderDirection {
	CREDIT("credit"),
	DEBIT("debit");

	private String direction;

	OrderDirection(String directionIn) {
		this.direction = directionIn;
	}

	public String getDirection() {
		return direction;
	}
}
