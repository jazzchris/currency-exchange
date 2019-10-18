package com.jazzchris.currencyexchange.stock;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jazzchris.currencyexchange.core.Quotations;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stocks implements Quotations, Serializable {

	private LocalDateTime publicationDate;
	
	@JsonDeserialize(using = ItemsDeserializer.class)
	private Map<Currency, Prices> items;
	
	public Stocks() {}

	@Override
	public LocalDateTime getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDateTime publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Map<Currency, Prices> getItems() {
		return items;
	}

	public void setItems(Map<Currency, Prices> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Stocks [publicationDate=" + publicationDate + ", items=" + items + "]";
	}

	@Override
	@JsonIgnore
	public Map<String, Prices> getAllItems() {
		Map<String, Prices> allItems = new HashMap<>();
		items.forEach((k, v) -> allItems.put(k.name(), v));
		return allItems;
	}	
}
