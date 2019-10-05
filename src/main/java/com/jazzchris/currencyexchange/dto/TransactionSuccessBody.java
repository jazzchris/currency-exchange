package com.jazzchris.currencyexchange.dto;

public class TransactionSuccessBody {

	private UserDto userDto;
	private String response;
	
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
}
