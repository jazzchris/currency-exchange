package com.jazzchris.currencyexchange.core;

public class Message {

	private boolean isSuccess;
	private String textMessage;
	
	public Message(String textMessage, boolean isSuccess) {
		this.textMessage = textMessage;
		this.isSuccess = isSuccess;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
}