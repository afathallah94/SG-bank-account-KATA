package com.sg.kata.bankaccount.maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sg.kata.bankaccount.model.Account;
import com.sg.kata.bankaccount.model.Client;
import com.sg.kata.bankaccount.model.Operation;

public class DataMaps {

	public static Map<String, Client> clients = new HashMap<>();
	public static Map<String, Account> accounts = new HashMap<>();
	public static Map<String, List<Operation>> operations = new HashMap<>();
}
