package com.jazzchris.currencyexchange.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler
	public String handleException(IOException exc) {

		return "login";
	}
		
}
