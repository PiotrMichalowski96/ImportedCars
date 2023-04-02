package com.piter.importedcars.validation;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SearchFromDateValidator implements ConstraintValidator<SearchFromDateConstraint, LocalDate> {

  private static final int MAX_SEARCH_YEARS_IN_HISTORY = 2;

  @Override
  public boolean isValid(LocalDate localDate, ConstraintValidatorContext cxt) {
    return localDate.isAfter(LocalDate.now().minusYears(MAX_SEARCH_YEARS_IN_HISTORY));
  }
}
