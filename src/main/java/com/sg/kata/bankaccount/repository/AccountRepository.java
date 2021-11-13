package com.sg.kata.bankaccount.repository;


import com.sg.kata.bankaccount.model.Account;

public interface AccountRepository {

	public Account findByAccountId(String accountId);
	public Account save(Account account);
}
