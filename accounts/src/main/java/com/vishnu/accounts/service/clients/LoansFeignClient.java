package com.vishnu.accounts.service.clients;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vishnu.accounts.model.Customer;
import com.vishnu.accounts.model.Loans;



@FeignClient("loans")
public interface LoansFeignClient {

	@PostMapping(value="/myLoans",consumes=MediaType.APPLICATION_JSON)
	public List<Loans> getLoansDetails( @RequestBody Customer customer);
}
