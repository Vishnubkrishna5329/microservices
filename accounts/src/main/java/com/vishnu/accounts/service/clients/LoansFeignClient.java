package com.vishnu.accounts.service.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import javax.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vishnu.accounts.model.Customer;
import com.vishnu.accounts.model.Loans;



@FeignClient("loans")
public interface LoansFeignClient {

	@PostMapping(value="/myLoans",consumes=MediaType.APPLICATION_JSON)
	public List<Loans> getLoansDetails(@RequestHeader("vizzbank-correlation-id") String correlationId, @RequestBody Customer customer);
}
