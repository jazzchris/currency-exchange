package com.jazzchris.currencyexchange.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.jazzchris.currencyexchange.stock.Currency;

public class UnitableValidator implements ConstraintValidator<Unitable, BigDecimal> {

	private Currency currency;
	
	@Override
	public void initialize(Unitable constraintAnnotation) {
		currency = constraintAnnotation.currency();
	}
	
	@Override
	public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
		boolean valid = true;
		if (value!=null) {
			valid = value.compareTo(new BigDecimal(0)) > -1 && divisibleByUnit(value, currency);
			if (!valid) {
				context.buildConstraintViolationWithTemplate("number must be rounded to " + currency.unit + " and at least 0")
					.addConstraintViolation()
					.disableDefaultConstraintViolation();
			}
		}
		return valid;
	}
	
	private boolean divisibleByUnit(BigDecimal value, Currency currency) {
		return value.remainder(new BigDecimal(currency.unit)).equals(new BigDecimal(0));
	}
}