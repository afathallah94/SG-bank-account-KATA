package com.sg.kata.bankaccount.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.kata.bankaccount.model.Operation;
import com.sg.kata.bankaccount.repository.OperationRepository;
import com.sg.kata.bankaccount.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {
	
	@Autowired
	OperationRepository operationRepository;

	@Override
	public Operation deposit(String amount, String accountId) {
		return operationRepository.deposit(amount, accountId);
	}

	@Override
	public Operation withdrawal(String amount, String accountId) {
		return operationRepository.withdrawal(amount, accountId);
	}

	@Override
	public List<Operation> history(String accountId) {
		return operationRepository.history(accountId);
	}

}
