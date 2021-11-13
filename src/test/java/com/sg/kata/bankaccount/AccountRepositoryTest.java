package com.sg.kata.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sg.kata.bankaccount.exceptions.BankAccountException;
import com.sg.kata.bankaccount.maps.DataMaps;
import com.sg.kata.bankaccount.model.Account;
import com.sg.kata.bankaccount.model.Client;
import com.sg.kata.bankaccount.repository.AccountRepository;

@SpringBootTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void testSaveInvalidAccount() {
		DataMaps.accounts.clear();
		Account account = new Account();
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		account.setAccountId("");
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		String accountId = "ACT123";
		account.setAccountId(accountId);
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		Client client = new Client();
		account.setClient(client);
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		client.setClientId("");
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		client.setClientId("CLT123");
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		client.setFirstName("");
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		client.setFirstName("FirstName");
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		client.setLastName("");
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		client.setLastName("LastName");
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		client.setBirthDate(LocalDate.of(1994, 4, 27));
		account.setBalance(-1d);
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));

		account.setBalance(0d);
		DataMaps.accounts.put(accountId, account);
		assertThrows(BankAccountException.class, () -> accountRepository.save(account));
	}

	@Test
	public void testValidAccount() {
		DataMaps.accounts.clear();
		DataMaps.clients.clear();
		Client client = new Client("CLT123", "lastName", "firstName", "address", LocalDate.of(1994, 4, 27));
		Account account = new Account();
		account.setAccountId("ACT123");
		account.setClient(client);
		accountRepository.save(account);
		assertEquals(DataMaps.accounts.get("ACT123"), account);
		assertEquals(DataMaps.clients.get("CLT123"), account.getClient());
		assertEquals(DataMaps.accounts.get("ACT123"), accountRepository.findByAccountId("ACT123"));
	}

	@Test
	public void testFindInvalidAccount() {
		assertThrows(BankAccountException.class, () -> accountRepository.findByAccountId(null));
		assertThrows(BankAccountException.class, () -> accountRepository.findByAccountId(""));
	}
}
