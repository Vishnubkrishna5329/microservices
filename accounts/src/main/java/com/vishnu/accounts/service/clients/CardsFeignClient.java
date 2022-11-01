package com.vishnu.accounts.service.clients;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vishnu.accounts.model.Cards;
import com.vishnu.accounts.model.Customer;


@FeignClient("cards")
public interface CardsFeignClient {

	@PostMapping(value="/myCards",consumes=MediaType.APPLICATION_JSON)
	public List<Cards> getCardDetails( @RequestBody Customer customer);
}
