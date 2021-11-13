package com.sg.kata.bankaccount;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sg.kata.bankaccount.constants.OperationTypeEnum;
import com.sg.kata.bankaccount.controller.OperationController;
import com.sg.kata.bankaccount.model.Account;
import com.sg.kata.bankaccount.model.Client;
import com.sg.kata.bankaccount.model.Operation;
import com.sg.kata.bankaccount.service.OperationService;

@WebMvcTest(OperationController.class)
@ExtendWith(SpringExtension.class)
public class OperationControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	OperationService operationService;

	@Test
	public void testDepositOperation() throws Exception {
		Client client = new Client("CLT123", "lastName", "firstName", "address", LocalDate.of(1994, 4, 27));
		Account account = new Account();
		account.setAccountId("ACT123");
		account.setBalance(500d);
		account.setClient(client);

		Operation operation = new Operation(OperationTypeEnum.DEPOSIT, "+" + 500, LocalDateTime.now(), account);

		Mockito.when(operationService.deposit("500", account.getAccountId())).thenReturn(operation);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/operations/deposit/ACT123/500"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.operationType", is(OperationTypeEnum.DEPOSIT.name())))
				.andExpect(jsonPath("$.account.accountId", is("ACT123")))
				.andExpect(jsonPath("$.account.balance", is(500d)));
	}

	@Test
	public void testWithdrawalOperation() throws Exception {
		Client client = new Client("CLT123", "lastName", "firstName", "address", LocalDate.of(1994, 4, 27));
		Account account = new Account();
		account.setAccountId("ACT123");
		account.setBalance(500d);
		account.setClient(client);
		Operation operation = new Operation(OperationTypeEnum.WITHDRAWAL, "-" + 200, LocalDateTime.now(), account);
		account.setBalance(300d);

		Mockito.when(operationService.withdrawal("200", account.getAccountId())).thenReturn(operation);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/operations/withdrawal/ACT123/200"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.operationType", is(OperationTypeEnum.WITHDRAWAL.name())))
				.andExpect(jsonPath("$.account.accountId", is("ACT123")))
				.andExpect(jsonPath("$.account.balance", is(300d)));
	}

	@Test
	public void testHistoryOperation() throws Exception {
		Client client = new Client("CLT123", "lastName", "firstName", "address", LocalDate.of(1994, 4, 27));
		Account account = new Account();
		account.setAccountId("ACT123");
		account.setBalance(500d);
		account.setClient(client);
		List<Operation> accountOperations = new ArrayList<>();
		Operation withdrawalOperation = new Operation(OperationTypeEnum.WITHDRAWAL, "-" + 200, LocalDateTime.now(),
				account);
		withdrawalOperation.getAccount().setBalance(300d);
		accountOperations.add(withdrawalOperation);

		Operation depositOperation = new Operation(OperationTypeEnum.DEPOSIT, "+" + 300,
				LocalDateTime.now().plusMinutes(2), account);
		depositOperation.getAccount().setBalance(600d);
		accountOperations.add(depositOperation);
		Collections.sort(accountOperations);
		Mockito.when(operationService.history(account.getAccountId())).thenReturn(accountOperations);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/history/ACT123")).andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue())).andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$[0].operationType", is(OperationTypeEnum.DEPOSIT.name())))
				.andExpect(jsonPath("$[1].operationType", is(OperationTypeEnum.WITHDRAWAL.name())));
	}
}
