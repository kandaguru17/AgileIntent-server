package io.app.agileintent.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IssueTypeContraintValidator implements ConstraintValidator<ValidIssueType, String> {

	private ValidIssueType annotation;

	@Override
	public void initialize(ValidIssueType annotation) {
		this.annotation=annotation;	
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		Object[] enumValues=this.annotation.enumClass().getEnumConstants();
		if(enumValues!=null) {
			for(Object enumValue:enumValues) {
				if(value.equals(enumValue)||(this.annotation.ignoreCase()&&value.equalsIgnoreCase(enumValue.toString()))){
					return true;
				}
			}
		}
		return false;
	}

}