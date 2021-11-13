package com.sg.kata.bankaccount.repository;

import java.util.List;

import com.sg.kata.bankaccount.model.Operation;

public interface OperationRepository {

	public Operation deposit(String amount, String accountId);
	public Operation withdrawal(String amount, String accountId);
	public List<Operation> history(String accountId);
}
