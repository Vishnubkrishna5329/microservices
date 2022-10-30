/**
 * 
 */
package com.vishnu.accounts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vishnu.accounts.config.AccountServiceConfig;
import com.vishnu.accounts.model.Accounts;
import com.vishnu.accounts.model.AccountsProperties;
import com.vishnu.accounts.model.Cards;
import com.vishnu.accounts.model.Customer;
import com.vishnu.accounts.model.CustomerDetails;
import com.vishnu.accounts.model.Loans;
import com.vishnu.accounts.repository.AccountsRepository;
import com.vishnu.accounts.service.clients.CardsFeignClient;
import com.vishnu.accounts.service.clients.LoansFeignClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class AccountsController {

	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private AccountServiceConfig accountConfig;

	@Autowired
	private LoansFeignClient loansFeignClient;

	@Autowired
	private CardsFeignClient cardsFeignClient;

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
		String jsonStr = obj.writeValueAsString(properties);
		return jsonStr;

	}

	@PostMapping("/customerDetails")
	@CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "myCustomerFallBack")
	// @Retry(name = "retryForCustomerDetails", fallbackMethod =
	// "myCustomerFallBack")
	public CustomerDetails getCustomerDetails(@RequestHeader("vizzbank-correlation-id") String correlationId,
			@RequestBody Customer customer) {

		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		List<Loans> loansDetails = loansFeignClient.getLoansDetails(correlationId, customer);
		List<Cards> cardDetails = cardsFeignClient.getCardDetails(correlationId, customer);

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setCards(cardDetails);
		customerDetails.setLoans(loansDetails);
		return customerDetails;

	}

	private CustomerDetails myCustomerFallBack(@RequestHeader("vizzbank-correlation-id") String correlationId,
			Customer customer, Throwable t) {
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		List<Loans> loansDetails = loansFeignClient.getLoansDetails(correlationId, customer);
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setLoans(loansDetails);
		return customerDetails;
	}

}
