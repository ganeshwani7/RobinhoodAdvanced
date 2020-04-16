package com.cheddar.robinhood.enums;

public enum OrderState {
	FILLED("filled"),
	CANCELLED("cancelled");

	private String orderState;

	OrderState (String orderStateIn) {
		this.orderState = orderStateIn;
	}

	public String getOrderState() {
		return orderState;
	}
}
