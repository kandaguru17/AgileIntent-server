package io.app.agileintent.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = IssueTypeContraintValidator.class)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIssueType {

	Class<? extends Enum<?>> enumClass();

	String message() default "Value is not with in the enum - {enumClass} ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	boolean ignoreCase() default false;
}
