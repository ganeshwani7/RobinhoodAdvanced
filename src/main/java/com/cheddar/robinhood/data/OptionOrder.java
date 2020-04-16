package com.cheddar.robinhood.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionOrder {
	private String cancel_url;

	private String canceled_quantity;

	private String created_at;

	private String direction;

	private String id;

	private List<Leg> legs;

	private String pending_quantity;

	private double premium;

	private double processed_premium;

	private double price;

	private double processed_quantity;

	private double quantity;

	private String ref_id;

	private String state;

	private String time_in_force;

	private String trigger;

	private String type;

	private String updated_at;

	private String chain_id;

	private String chain_symbol;

	private String response_category;

	private String opening_strategy;

	private String closing_strategy;

	private String stop_price;
}
