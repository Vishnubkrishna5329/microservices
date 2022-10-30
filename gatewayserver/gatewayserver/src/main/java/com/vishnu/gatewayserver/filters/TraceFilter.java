package com.vishnu.gatewayserver.filters;

import org.hibernate.validator.internal.util.privilegedactions.IsClassPresent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Order(1)
@Component
public class TraceFilter implements GlobalFilter{

	private static final Logger LOGGER= LoggerFactory.getLogger(TraceFilter.class);
	
	@Autowired
	FilterUtility filterUtility;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		if (isCorrelationIdPresent(requestHeaders)) {
			LOGGER.debug("VizzBank-correlation-id found in tracing filter: {}. ",
					filterUtility.getCorrelationId(requestHeaders));
		} else {
			String correlationID = generateCorrelationId();
			exchange = filterUtility.setCorrelationId(exchange, correlationID);
			LOGGER.debug("VizzBank-correlation-id generated in tracing filter: {}.", correlationID);
		}
		return chain.filter(exchange);
	}
	

	private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
		if (filterUtility.getCorrelationId(requestHeaders) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	private String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}

}
