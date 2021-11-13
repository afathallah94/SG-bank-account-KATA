package com.sg.kata.bankaccount;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.kata.bankaccount.controller.AccountController;
import com.sg.kata.bankaccount.model.Account;
import com.sg.kata.bankaccount.model.Client;
import com.sg.kata.bankaccount.service.AccountService;

@WebMvcTest(AccountController.class)
@ExtendWith(SpringExtension.class)
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private AccountService accountService;

	@Test
	public void testFindByAccountId() throws Exception {
		Client client = new Client("CLT123", "lastName", "firstName", "address", LocalDate.of(1994, 4, 27));
		Account account = new Account();
		account.setAccountId("ACT123");
		account.setClient(client);
		Mockito.when(accountService.findByAccountId(account.getAccountId())).thenReturn(account);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/account/ACT123").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.accountId", is("ACT123")));
	}

	@Test
	public void testSaveAccount_success() throws Exception {
		Client client = new Client("CLT123", "lastName", "firstName", "address", LocalDate.of(1994, 4, 27));
		Account account = new Account();
		account.setAccountId("ACT123");
		account.setClient(client);

		Mockito.when(accountService.save(account)).thenReturn(account);

		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/api/account/save")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(account));

		mockMvc.perform(mockRequestBuilder).andExpect(status().isCreated()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.accountId", is("ACT123")));
	}
}
