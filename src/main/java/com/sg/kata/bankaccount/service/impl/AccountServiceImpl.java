package com.sg.kata.bankaccount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.kata.bankaccount.model.Account;
import com.sg.kata.bankaccount.repository.AccountRepository;
import com.sg.kata.bankaccount.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public Account findByAccountId(String accountId) {
		return this.accountRepository.findByAccountId(accountId);
	}

	@Override
	public Account save(Account account) {
		return this.accountRepository.save(account);
	}
}
