package com.cheddar.robinhood.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leg {
	private List<Execution> executions;

	private String id;

	private String option;

	private String position_effect;

	private int ratio_quantity;

	private String side;

}
