package io.app.agileintent.validators;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import io.app.agileintent.exceptions.UserProfileException;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {

		if(password==null)
			throw new UserProfileException("Password Cannot be null");
		
		
		PasswordValidator validator = new PasswordValidator(Arrays.asList(
				// at least 8 characters
				//new LengthRule(8, 15),

				// at least one upper-case character
				new CharacterRule(EnglishCharacterData.UpperCase, 1),

				// at least one lower-case character
				new CharacterRule(EnglishCharacterData.LowerCase, 1),

				// at least one digit character
				new CharacterRule(EnglishCharacterData.Digit, 1),

				// at least one symbol (special character)
				new CharacterRule(EnglishCharacterData.Special, 1),

				// no whitespace
				new WhitespaceRule()

		));

		RuleResult result = validator.validate(new PasswordData(password));
	
		if (result.isValid()) {
			return true;
		}
		
		List<String> messages = validator.getMessages(result);

		String messageTemplate = messages.stream().collect(Collectors.joining(","));

	
		context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation()
				.disableDefaultConstraintViolation();
		
		return false;
	}

}
