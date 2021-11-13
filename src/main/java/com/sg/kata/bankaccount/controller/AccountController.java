package com.sg.kata.bankaccount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sg.kata.bankaccount.model.Account;
import com.sg.kata.bankaccount.service.AccountService;

@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

	@Autowired
	AccountService accountService;

	@GetMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> findByAccountId(@PathVariable("accountId") String accountId) {
		return new ResponseEntity<Account>(accountService.findByAccountId(accountId), HttpStatus.OK);
	}

	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> save(@RequestBody Account account) {
		return new 	ResponseEntity<Account>(accountService.save(account), HttpStatus.CREATED);
	}

}
