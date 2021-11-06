package com.piter.importedcars.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = SearchFromDateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchFromDateConstraint {
  String message() default "Invalid search from date - maximum 2 years ago";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
