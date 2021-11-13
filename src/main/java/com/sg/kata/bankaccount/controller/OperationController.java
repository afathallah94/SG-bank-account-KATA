package com.sg.kata.bankaccount.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sg.kata.bankaccount.model.Operation;
import com.sg.kata.bankaccount.service.OperationService;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

	@Autowired
	OperationService operationService;

	@PutMapping(value = "/deposit/{accountId}/{amount}")
	public ResponseEntity<Operation> deposit(@PathVariable("amount") String amount,
			@PathVariable("accountId") String accountId) {
		return new ResponseEntity<Operation>(operationService.deposit(amount, accountId), HttpStatus.CREATED);
	}

	@PutMapping(value = "/withdrawal/{accountId}/{amount}")
	public ResponseEntity<Operation> withdrawal(@PathVariable("amount") String amount,
			@PathVariable("accountId") String accountId) {
		return new ResponseEntity<Operation>(operationService.withdrawal(amount, accountId), HttpStatus.CREATED);
	}

	@GetMapping(value = "/history/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Operation>> history(@PathVariable("accountId") String accountId) {
		return new ResponseEntity<List<Operation>>(operationService.history(accountId), HttpStatus.OK);
	}

}
