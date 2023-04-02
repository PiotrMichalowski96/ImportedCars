package com.piter.importedcars.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SearchFromDateValidatorTest {

  private final SearchFromDateValidator searchFromDateValidator = new SearchFromDateValidator();

  @ParameterizedTest
  @CsvSource({"1, true", "3, false"})
  void shouldValidateSearchDate(int years, boolean validationResult) {
    //given
    LocalDate searchDate = LocalDate.now().minusYears(years);

    //when
    boolean valid = searchFromDateValidator.isValid(searchDate, null);

    //then
    assertThat(valid).isEqualTo(validationResult);
  }
}