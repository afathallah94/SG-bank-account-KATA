package com.sg.kata.bankaccount.repository.impl;

import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.sg.kata.bankaccount.exceptions.BankAccountException;
import com.sg.kata.bankaccount.maps.DataMaps;
import com.sg.kata.bankaccount.model.Account;
import com.sg.kata.bankaccount.repository.AccountRepository;
import com.sg.kata.bankaccount.utils.BankAccountUtils;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

	@Override
	public Account findByAccountId(String accountId) {
		if (BankAccountUtils.isNullOrEmpty(accountId)) {
			throw new BankAccountException("FIND_ACCOUNT_INVALID_ACCOUNT_ID", "Invalid Account ID");
		}
		Account account = DataMaps.accounts.get(accountId);
		return account;
	}

	@Override
	public synchronized Account save(Account account) {
		if (BankAccountUtils.isNullOrEmpty(account.getAccountId())) {
			throw new BankAccountException("SAVE_ACCOUNT_INVALID_ACCOUNT_ID", "Invalid Account ID");
		}
		if (Objects.isNull(account.getClient())) {
			throw new BankAccountException("SAVE_ACCOUNT_INVALID_CLIENT", "Invalid Client");
		}
		if (BankAccountUtils.isNullOrEmpty(account.getClient().getClientId())) {
			throw new BankAccountException("SAVE_ACCOUNT_INVALID_ACCOUNT_CLIENT_ID", "Invalid Account Client ID");
		}
		if (BankAccountUtils.isNullOrEmpty(account.getClient().getFirstName())) {
			throw new BankAccountException("SAVE_ACCOUNT_INVALID_ACCOUNT_CLIENT_FIRST_NAME",
					"Invalid Account Client First Name");
		}
		if (BankAccountUtils.isNullOrEmpty(account.getClient().getLastName())) {
			throw new BankAccountException("SAVE_ACCOUNT_INVALID_ACCOUNT_CLIENT_LAST_NAME",
					"Invalid Account Client Last Name");
		}
		if (account.getClient().getBirthDate() == null) {
			throw new BankAccountException("SAVE_ACCOUNT_INVALID_ACCOUNT_CLIENT_BIRTH_DATE",
					"Invalid Account Client Birth Date");
		}
		if (account.getBalance() < 0d) {
			throw new BankAccountException("SAVE_ACCOUNT_INVALID_BALANCE",
					"The balance must be equal or greater than 0");
		}
		if (DataMaps.accounts.containsKey(account.getAccountId())) {
			throw new BankAccountException("SAVE_ACCOUNT_ALREADY_EXISTS", "Account already exists");
		}
		DataMaps.accounts.put(account.getAccountId(), account);
		DataMaps.clients.put(account.getClient().getClientId(), account.getClient());
		return account;
	}

}
