/**
 * 
 */
package com.vishnu.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vishnu.accounts.config.AccountServiceConfig;
import com.vishnu.accounts.model.Accounts;
import com.vishnu.accounts.model.AccountsProperties;
import com.vishnu.accounts.model.Customer;
import com.vishnu.accounts.repository.AccountsRepository;


@RestController
public class AccountsController {

	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private AccountServiceConfig accountConfig;

	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {

		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}

	}

	@GetMapping("/account/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter obj = new ObjectMapper().writer().withDefaultPrettyPrinter();
		AccountsProperties properties = new AccountsProperties(accountConfig.getMsg(), accountConfig.getBuildVersion(),
				accountConfig.getMailDetails(), accountConfig.getActiveBranches());
		String jsonStr= obj.writeValueAsString(properties);
		return jsonStr;

	}

}
