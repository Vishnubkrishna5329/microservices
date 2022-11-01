/**
 * 
 */
package com.vishnu.cards.controller;

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
import com.vishnu.cards.config.CardsServiceConfig;
import com.vishnu.cards.model.Cards;
import com.vishnu.cards.model.CardsProperties;
import com.vishnu.cards.model.Customer;
import com.vishnu.cards.repository.CardsRepository;

/**
 * @author Eazy Bytes
 *
 */

@RestController
public class CardsController {

	private static Logger LOGGER = LoggerFactory.getLogger(CardsController.class);

	@Autowired
	private CardsRepository cardsRepository;
	
	@Autowired
	CardsServiceConfig cardConfig;
	
	@PostMapping("/myCards")
	public List<Cards> getCardDetails(@RequestBody Customer customer) {
		LOGGER.info("CardDetails API invoked");
		List<Cards> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
		LOGGER.info("CardDetails API generated response");
		if (cards != null) {
			return cards;
		} else {
			return null;
		}

	}
	
	@GetMapping("cards/properties")
	public String getProperties() throws JsonProcessingException {
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		CardsProperties cardsProperties = new CardsProperties(cardConfig.getMsg(), cardConfig.getBuildVersion(), cardConfig.getMailDetails(), cardConfig.getActiveBranches());
		return objectWriter.writeValueAsString(cardsProperties);
	}

}
