package com.cheddar.robinhood.advanced;

import com.cheddar.robinhood.client.RobinhoodClient;
import com.cheddar.robinhood.data.Portfolio;
import com.cheddar.robinhood.data.Transfer;
import com.cheddar.robinhood.exception.RobinhoodException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Investments {
	RobinhoodClient robinhoodClient;

	public Investments (RobinhoodClient robinhoodClientIn) {
		robinhoodClient = robinhoodClientIn;
	}

	public void getPortfolio() {
		try {
			Portfolio portfolio = robinhoodClient.getPortfolio();

		} catch (RobinhoodException e) {
			e.printStackTrace();
		}

	}

	// Get transfers over the last month
	// Get transfers from the beginning
	// Get transfers from the last year
	public List<Transfer> getAllDeposits() throws RobinhoodException {
		return robinhoodClient.
				getTransfers()
				.stream()
				.filter(transfer -> transfer.getDirection().equals("deposit"))
				.collect(Collectors.toList());
	}

	public List<Transfer> getAllWithdraws() throws RobinhoodException {
		return robinhoodClient.
				getTransfers()
				.stream()
				.filter(transfer -> transfer.getDirection().equals("withdraw"))
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

		System.out.println("Amount deposited in last month is " + calculateTotals(getDepositsOverTheLastMonth()));
		System.out.println("Amount deposited in 6 months is " + calculateTotals(getDepositsInLastSixMonths()));
		System.out.println("Amount deposited in a year is " + calculateTotals(getDepositsInLastYear()));
		System.out.println("Amount deposited from the begining is " + calculateTotals(transfers));
		System.out.println();

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
					System.out.print("Amount: " + transfer.getAmount());
					System.out.print(" " + transfer.getDirection() + "  ");
					System.out.print(" On : " + transfer.getCreated_at());
					System.out.println();
				}
		);
		System.out.println();
		transfers = getAllWithdraws();

		System.out.println("Total amount withdrawn " + calculateTotals(transfers));

		transfers.forEach(
				transfer -> {
					System.out.print("Amount: " + transfer.getAmount());
					System.out.print(" " + transfer.getDirection() + "  ");
					System.out.print(" On : " + transfer.getCreated_at());
					System.out.println();
				}
		);
	}
}
