package com.piter.importedcars.validation;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SearchFromDateValidator implements ConstraintValidator<SearchFromDateConstraint, LocalDate> {

  @Override
  public void initialize(SearchFromDateConstraint searchFromDateConstraint) {
  }

  @Override
  public boolean isValid(LocalDate localDate, ConstraintValidatorContext cxt) {
    return localDate.isAfter(LocalDate.now().minusYears(2));
  }
}
