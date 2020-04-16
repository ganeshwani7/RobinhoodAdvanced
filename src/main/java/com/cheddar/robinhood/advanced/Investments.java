package com.cheddar.robinhood.advanced;

import com.cheddar.robinhood.client.RobinhoodClient;
import com.cheddar.robinhood.data.Instrument;
import com.cheddar.robinhood.data.OptionOrder;
import com.cheddar.robinhood.enums.OrderDirection;
import com.cheddar.robinhood.data.Transfer;
import com.cheddar.robinhood.enums.OrderState;
import com.cheddar.robinhood.exception.RobinhoodException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class Investments {
	RobinhoodClient robinhoodClient;
	Map<String, Instrument> instrumentMap = new HashMap<>();

	public Investments (RobinhoodClient robinhoodClientIn) {
		robinhoodClient = robinhoodClientIn;
	}

	public Collection<OptionOrder> getAllOptionsOrdersTillDate () throws RobinhoodException {
		return robinhoodClient.getOptionOrdersAfterDate(new DateTime().minusYears(20).toDate());
	}


	/**
	 * Groups option orders for trade symbols
	 * @param optionOrders
	 * @return
	 */
	public Map<String, List<OptionOrder>> combineOptionOrdersBySymbol (Collection<OptionOrder> optionOrders) {
		Map<String, List<OptionOrder>> groupedOptionOrders = new HashMap<>();

		optionOrders.forEach(optionOrder -> {
			String symbol = optionOrder.getChain_symbol();
			List<OptionOrder> orders = groupedOptionOrders.get(symbol);

			if (orders == null) {
				orders = new ArrayList<>();
				groupedOptionOrders.put(symbol, orders);
			}

			orders.add(optionOrder);
		});

		return groupedOptionOrders;
	}

	/**
	 * Shows unrealized gain/loss (because it shows money invested as well)
	 *
	 * Doesn't deal with legs of the position
	 *
	 * @throws RobinhoodException
	 */
	public void printUnrealizedOptionReturns () throws RobinhoodException {
		Collection<OptionOrder> optionOrders = getAllOptionsOrdersTillDate();

		Map<String, List<OptionOrder>> groupedOptionOrders = combineOptionOrdersBySymbol(optionOrders);

		AtomicLong totalUnrealizedDebit = new AtomicLong();
		AtomicLong totalUnrealizedCredit = new AtomicLong();

		groupedOptionOrders.
				forEach((symbol, orders) -> {
			long credit = 0, debit = 0;

			orders = orders
					.stream()
					.filter(optionOrder -> optionOrder.getState().equals(OrderState.FILLED.getOrderState()))
					.collect(Collectors.toList());

			for (OptionOrder order : orders) {

				if (order.getDirection().equals(OrderDirection.CREDIT.getDirection())) {
					credit += order.getPremium();
				} else if (order.getDirection().equals(OrderDirection.DEBIT.getDirection())) {
					debit += order.getPremium();
				} else {
					log.error("Direction of the trade doesn't make sense!!");
				}

			}

			totalUnrealizedDebit.addAndGet(credit);
			totalUnrealizedCredit.addAndGet(debit);

			if (credit > debit) {
				log.info("Position with {} is in profit of {}", symbol, credit - debit);
			} else {
				log.info("Position with {} is in loss of {}", symbol, debit - credit);
			}

		});

		log.info("Total unrealized credit {}", totalUnrealizedCredit.get());
		log.info("Total unrealized debit {}", totalUnrealizedDebit.get());


	}

	// Get transfers over the last month
	// Get transfers from the beginning
	// Get transfers from the last year
	public List<Transfer> getAllDeposits() throws RobinhoodException {
		return robinhoodClient.
				getTransfers()
				.stream()
				.filter(transfer -> transfer.getDirection().equals("deposit"))
				.filter(transfer -> transfer.getState().equals("completed"))
				.collect(Collectors.toList());
	}

	public List<Transfer> getAllWithdraws() throws RobinhoodException {
		return robinhoodClient.
				getTransfers()
				.stream()
				.filter(transfer -> transfer.getDirection().equals("withdraw"))
				.filter(transfer -> transfer.getState().equals("completed"))
				.collect(Collectors.toList());
	}

	public List<Transfer> getDepositsOverTheLastMonth() throws RobinhoodException {
		List< Transfer> transfers = getAllDeposits()
				.stream()
				.filter(transfer -> transfer.getUpdated_at().after(new DateTime().minusMonths(1).toDate()))
				.collect(Collectors.toList());

		return transfers;
	}

	public List<Transfer> getDepositsInLastYear() throws RobinhoodException {
		return getAllDeposits()
						.stream()
						.filter(transfer -> transfer.getUpdated_at().after(new DateTime().minusYears(1).toDate()))
						.collect(Collectors.toList());
	}

	public List<Transfer> getDepositsInLastSixMonths() throws RobinhoodException {
		return getAllDeposits()
				.stream()
				.filter(transfer -> transfer.getUpdated_at().after(new DateTime().minusMonths(6).toDate()))
				.collect(Collectors.toList());
	}


	public Float calculateTotals (List<Transfer> transfers) {
		float amountTransferred = 0;
		for (Transfer transfer : transfers) {
			amountTransferred += transfer.getAmount();
		}
		return amountTransferred;
	}

	public void printTransferInfo () throws RobinhoodException {
		List<Transfer> transfers = getAllDeposits();

		log.info("Amount deposited in last month is {}", calculateTotals(getDepositsOverTheLastMonth()));
		log.info("Amount deposited in 6 months is {}", calculateTotals(getDepositsInLastSixMonths()));
		log.info("Amount deposited in a year is {}", calculateTotals(getDepositsInLastYear()));
		log.info("Amount deposited from the begining is {}", calculateTotals(getAllDeposits()));

		log.info("Amount deposited in last month is " + calculateTotals(getDepositsOverTheLastMonth()));
		log.info("Amount deposited in 6 months is " + calculateTotals(getDepositsInLastSixMonths()));
		log.info("Amount deposited in a year is " + calculateTotals(getDepositsInLastYear()));
		log.info("Amount deposited from the begining is " + calculateTotals(transfers));
		log.info("\n");

		Collections.sort(transfers, new Comparator<Transfer>() {
			@Override
			public int compare(Transfer o1, Transfer o2) {
				if (o1.getCreated_at().after(o2.getCreated_at())) {
					return 1;
				}
				if (o1.getCreated_at().before(o2.getCreated_at())) {
					return -1;
				}
				return 0;
			}
		});

		transfers.forEach(
				transfer -> {
					log.info("Amount: " + transfer.getAmount());
					log.info(" " + transfer.getDirection() + "  ");
					log.info(" On : " + transfer.getCreated_at());
					log.info("\n");
				}
		);
		log.info("\n");
		transfers = getAllWithdraws();

		log.info("Total amount withdrawn " + calculateTotals(transfers));

		transfers.forEach(
				transfer -> {
					log.info("Amount: " + transfer.getAmount());
					log.info(" " + transfer.getDirection() + "  ");
					log.info(" On : " + transfer.getCreated_at());
					log.info("\n");
				}
		);
	}
}
