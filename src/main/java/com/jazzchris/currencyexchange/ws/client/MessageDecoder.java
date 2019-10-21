package com.jazzchris.currencyexchange.ws.client;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jazzchris.currencyexchange.stock.Stocks;

public class MessageDecoder implements Decoder.Text<Stocks> {

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.registerModule(new JavaTimeModule()).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
			.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
	
	@Override
	public Stocks decode(String s) {
		System.err.println("Inside decoder");
		Stocks stocks = null;
		try {
			stocks = MAPPER.readValue(s, Stocks.class);
		} catch (Exception e) {
			System.out.println("Decoding error");
		}
		return stocks;
	}

	@Override
	public boolean willDecode(String s) {
		return (s != null && s.length() > 0);
	}
	
	// unused:
	@Override
	public void init(EndpointConfig endpointConfig) {}

	@Override
	public void destroy() {}

}
