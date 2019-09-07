package com.jazzchris.currencyexchange.stock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ItemsDeserializer extends StdDeserializer<HashMap<Currency, Prices>> {

	private static final long serialVersionUID = 1L;

	public ItemsDeserializer() {
	       super(HashMap.class);
	}
	
	protected ItemsDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public HashMap<Currency, Prices> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		HashMap<Currency, Prices> deserialized = new HashMap<Currency, Prices>();
		JsonNode node = p.getCodec().readTree(p);
		if (node.isArray()) {
			for (JsonNode n : (ArrayNode) node) {
				JsonNode curr = Optional.ofNullable(n.get("code")).orElse(n.get("Code"));
				JsonNode purchasePrice = Optional.ofNullable(n.get("purchasePrice")).orElse(n.get("PurchasePrice"));
				JsonNode sellPrice = Optional.ofNullable(n.get("sellPrice")).orElse(n.get("SellPrice"));
				JsonNode averagePrice = Optional.ofNullable(n.get("averagePrice")).orElse(n.get("AveragePrice"));
				deserialized.put(Currency.valueOf(curr.asText()), new Prices(purchasePrice.decimalValue(),
						sellPrice.decimalValue(), averagePrice.decimalValue()));
			}
		}
		return deserialized;
	}
}
