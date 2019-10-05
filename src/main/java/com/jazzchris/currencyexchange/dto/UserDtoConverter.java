package com.jazzchris.currencyexchange.dto;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jazzchris.currencyexchange.core.EntityConverter;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.stock.Currency;

public class UserDtoConverter implements EntityConverter<Users, UserDto> {

	@Override
	public UserDto convert(Users from) {
		UserDto userDto = new UserDto();
		Map<Currency, BigDecimal> wallet = new LinkedHashMap<>();
		userDto.setFullName(from.getFirstName() + " " + from.getLastName());
		from.getUsersWallet().collectForeign().values().stream()
			.forEach(k -> wallet.put(k.getProperty(), k.getValue().divide(BigDecimal.valueOf(k.getProperty().unit))));
		userDto.setWallet(wallet);
		userDto.setPln(from.getUsersWallet().getPln());
		return userDto;
	}

}
