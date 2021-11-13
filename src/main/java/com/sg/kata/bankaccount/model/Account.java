package com.sg.kata.bankaccount.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {

	private String accountId;
	private double balance = 0d;
	private Client client;
	private double minBalance = -300d;
	
	public void deposit(double amount) {
		this.balance+=amount;
	}
	
	public void withdraw(double amount) {
		this.balance-=amount;
	}
}
