package com.sg.kata.bankaccount.service;

import com.sg.kata.bankaccount.model.Account;

public interface AccountService {
	
	public Account findByAccountId(String accountId);
	public Account save(Account account);

}
