package com.sg.kata.bankaccount.repository.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sg.kata.bankaccount.constants.OperationTypeEnum;
import com.sg.kata.bankaccount.exceptions.BankAccountException;
import com.sg.kata.bankaccount.maps.DataMaps;
import com.sg.kata.bankaccount.model.Account;
import com.sg.kata.bankaccount.model.Operation;
import com.sg.kata.bankaccount.repository.AccountRepository;
import com.sg.kata.bankaccount.repository.OperationRepository;

@Repository
public class OperationRepositoryImpl implements OperationRepository {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public synchronized Operation deposit(String amount, String accountId) {
		Account account = accountRepository.findByAccountId(accountId);
		if (account == null) {
			throw new BankAccountException("DEPOSIT_OPERATION_NOT_FOUND", "Deposit Operation : Not Found Account");
		}
		List<Operation> accountOperations = DataMaps.operations.get(accountId);
		if (accountOperations == null) {
			accountOperations = new ArrayList<>();
		}
		double amountDouble;
		try {
			amountDouble = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("The amount should be a double");
		}
		if (amountDouble == 0.0) {
			throw new BankAccountException("DEPOSIT_OPERATION_INVALID_AMOUNT",
					"Deposit Operation : The amount should be greater than 0");
		}
		account.deposit(amountDouble);
		Operation operation = new Operation(OperationTypeEnum.DEPOSIT, "+" + amount, LocalDateTime.now(), account);
		accountOperations.add(operation);
		DataMaps.operations.put(accountId, accountOperations);
		return operation;
	}

	@Override
	public synchronized Operation withdrawal(String amount, String accountId) {
		Account account = accountRepository.findByAccountId(accountId);
		if (account == null) {
			throw new BankAccountException("WITHDRAWAL_OPERATION_NOT_FOUND",
					"Withdrawal Operation : Not Found Account");
		}
		double amountDouble;
		try {
			amountDouble = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("The amount should be a double");
		}
		if (amountDouble == 0.0) {
			throw new BankAccountException("WITHDRAWAL_OPERATION_INVALID_AMOUNT",
					"Withdrawal Operation : The amount should be greater than 0");
		}
		if (account.getBalance() - amountDouble < account.getMinBalance()) {
			throw new BankAccountException("WITHDRAWAL_OPERATION_MINIMUM_BALANCE_REACHED",
					"Withdrawal Operation : The minimum balance is reached");
		}
		List<Operation> accountOperations = DataMaps.operations.get(accountId);
		if (accountOperations == null) {
			throw new BankAccountException("WITHDRAWAL_OPERATION_UNAUOTHORIZED_OPERATION",
					"Withdrawal Operation : You need to deposit first");
		}
		account.withdraw(amountDouble);
		Operation operation = new Operation(OperationTypeEnum.WITHDRAWAL, "-" + amount, LocalDateTime.now(), account);
		accountOperations.add(operation);
		DataMaps.operations.put(accountId, accountOperations);
		return operation;
	}

	@Override
	public List<Operation> history(String accountId) {
		Account account = accountRepository.findByAccountId(accountId);
		if (account == null) {
			throw new BankAccountException("HISTORY_OPERATION_NOT_FOUND", "History Operation : Not Found Account");
		} else {
			if (DataMaps.operations.get(accountId) != null) {
				List<Operation> accountOperations = new ArrayList<>(DataMaps.operations.get(accountId));
				Collections.sort(accountOperations);
				return accountOperations;
			}
			return Collections.emptyList();
		}
	}
}
