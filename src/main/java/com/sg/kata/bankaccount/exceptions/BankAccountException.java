package com.sg.kata.bankaccount.exceptions;

public class BankAccountException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1855946329604221233L;
	
	String code;
	
	public BankAccountException(String code, String message) {
		super(message);
		this.code = code;
	}

}
