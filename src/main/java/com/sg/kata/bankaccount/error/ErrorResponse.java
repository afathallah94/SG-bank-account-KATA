package com.sg.kata.bankaccount.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	
	private final int code;
	private final String message;

}
