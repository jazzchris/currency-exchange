package com.jazzchris.currencyexchange.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class PasswordCheckedValidator implements ConstraintValidator<PasswordChecked, Object> {

	private String firstField;
	private String secondField;
	private String message;
	
	@Override
	public void initialize(PasswordChecked constraintAnnotation) {
		firstField = constraintAnnotation.first();
		secondField = constraintAnnotation.second();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		boolean valid = true;
		try {
			final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstField);
			final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondField);
			valid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
		} catch (final Exception e) {}
		if (!valid) {
			context.buildConstraintViolationWithTemplate(message)
				.addPropertyNode(firstField)
				.addConstraintViolation()
				.disableDefaultConstraintViolation();
		}
		return valid;
	}
}
