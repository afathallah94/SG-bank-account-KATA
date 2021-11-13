package com.sg.kata.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sg.kata.bankaccount.constants.OperationTypeEnum;
import com.sg.kata.bankaccount.exceptions.BankAccountException;
import com.sg.kata.bankaccount.maps.DataMaps;
import com.sg.kata.bankaccount.model.Account;
import com.sg.kata.bankaccount.model.Client;
import com.sg.kata.bankaccount.model.Operation;
import com.sg.kata.bankaccount.repository.OperationRepository;

@SpringBootTest
public class OperationRepositoryTest {

	@Autowired
	OperationRepository operationRepository;

	@Test
	public void testInvalidOperations() {
		DataMaps.accounts.clear();
		Client client = new Client("CLT123", "lastName", "firstName", "address", LocalDate.of(1994, 4, 27));
		Account account = new Account();
		account.setAccountId("ACT123");
		account.setClient(client);
		DataMaps.accounts.put("ACT123", account);
		assertThrows(BankAccountException.class, () -> operationRepository.deposit("500", "ACT500"));
		assertThrows(BankAccountException.class, () -> operationRepository.withdrawal("500", "ACT500"));
		assertThrows(BankAccountException.class, () -> operationRepository.history("ACT500"));
		assertThrows(NumberFormatException.class, () -> operationRepository.deposit("abc", account.getAccountId()));
		assertThrows(NumberFormatException.class, () -> operationRepository.withdrawal("abc", account.getAccountId()));
		assertThrows(BankAccountException.class, () -> operationRepository.deposit("0", account.getAccountId()));
		assertThrows(BankAccountException.class, () -> operationRepository.withdrawal("0", account.getAccountId()));
		account.setBalance(500d);
		assertThrows(BankAccountException.class, () -> operationRepository.withdrawal("900", account.getAccountId()));
		assertThrows(BankAccountException.class, () -> operationRepository.withdrawal("100", account.getAccountId()));
	}

	@Test
	public void testValidOperations() {
		DataMaps.accounts.clear();
		Client client = new Client("CLT123", "lastName", "firstName", "address", LocalDate.of(1994, 4, 27));
		Account account1 = new Account();
		account1.setAccountId("ACT123");
		account1.setClient(client);
		DataMaps.accounts.put(account1.getAccountId(), account1);
		assertEquals(Collections.emptyList(), operationRepository.history(account1.getAccountId()));

		Operation depositOperation = operationRepository.deposit("200", account1.getAccountId());
		Operation depositOperationExpected = new Operation(OperationTypeEnum.DEPOSIT, "+" + 200,
				depositOperation.getTimestamp(), account1);
		assertEquals(depositOperationExpected, depositOperation);

		Operation withdrawalOperation = operationRepository.withdrawal("200", account1.getAccountId());
		withdrawalOperation.setTimestamp(withdrawalOperation.getTimestamp().plusHours(1));
		Operation withdrawalOperationExpected = new Operation(OperationTypeEnum.WITHDRAWAL, "-" + 200,
				withdrawalOperation.getTimestamp(), account1);
		assertEquals(withdrawalOperationExpected, withdrawalOperation);

		List<Operation> accountOperationsExpected = new ArrayList<>();
		accountOperationsExpected.add(withdrawalOperationExpected);
		accountOperationsExpected.add(depositOperationExpected);
		Collections.sort(accountOperationsExpected);
		assertEquals(accountOperationsExpected, operationRepository.history(account1.getAccountId()));
	}
}
