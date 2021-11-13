package com.sg.kata.bankaccount.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sg.kata.bankaccount.error.ErrorResponse;
import com.sg.kata.bankaccount.exceptions.BankAccountException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BankAccountControllerAdvice {

	@ExceptionHandler(BankAccountException.class)
	public ResponseEntity<ErrorResponse> handleException(BankAccountException bankAccountException) {
		log.error(bankAccountException.getMessage());
		if (bankAccountException.getMessage() != null
				&& bankAccountException.getMessage().toUpperCase().contains("NOT FOUND")) {
			return new ResponseEntity<ErrorResponse>(
					new ErrorResponse(HttpStatus.NOT_FOUND.value(), bankAccountException.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ErrorResponse>(
				new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), bankAccountException.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
