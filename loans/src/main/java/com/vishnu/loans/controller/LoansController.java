/**
 * 
 */
package com.vishnu.loans.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vishnu.loans.config.LoansConfig;
import com.vishnu.loans.model.Customer;
import com.vishnu.loans.model.Loans;
import com.vishnu.loans.model.LoansProperties;
import com.vishnu.loans.repository.LoansRepository;

/**
 * @author Eazy Bytes
 *
 */

@RestController
public class LoansController {
	private static Logger LOGGER = LoggerFactory.getLogger(LoansController.class);

	
	
	@Autowired
	private LoansRepository loansRepository;

	@Autowired
	private LoansConfig loanConfig;
	
	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestBody Customer customer) {
		LOGGER.info("Loan Details API invoked");
		List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		LOGGER.info("Loan Details API generated response");
		if (loans != null) {
			return loans;
		} else {
			return null;
		}

	}
	
	@GetMapping("/loans/properties")
	public String getProperties() throws JsonProcessingException {
		ObjectWriter obj= new ObjectMapper().writer().withDefaultPrettyPrinter();
		LoansProperties properties= new LoansProperties(loanConfig.getMsg(), loanConfig.getBuildVersion(), loanConfig.getMailDetails(), loanConfig.getActiveBranches());
		return obj.writeValueAsString(properties);
	}

}
