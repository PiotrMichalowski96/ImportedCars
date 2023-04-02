package com.piter.importedcars.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MapperMethodsTest {

  private final MapperMethods mapperMethods = new MapperMethods() {};

  @ParameterizedTest
  @MethodSource("provideCepikDateAndLocalDate")
  void shouldMapCepikDateToLocalDate(String cepikDate, LocalDate expectedLocalDate) {
    //given input
    //when
    LocalDate mappedLocalDate = mapperMethods.cepikDateToLocalDateMapper(cepikDate);

    //then
    assertThat(mappedLocalDate).isEqualTo(expectedLocalDate);
  }

  private static Stream<Arguments> provideCepikDateAndLocalDate() {
    return Stream.of(
        Arguments.of(StringUtils.EMPTY, null),
        Arguments.of(null, null),
        Arguments.of("2021-10-15", LocalDate.of(2021, 10, 15))
    );
  }

  @ParameterizedTest
  @MethodSource("provideLocalDateAndCepikDate")
  void shouldMapLocalDateToCepikDate(LocalDate inputLocalDate, String expectedCepikDate) {
    //given input
    //when
    String mappedCepikDate = mapperMethods.localDateToCepikDateMapper(inputLocalDate);

    //then
    assertThat(mappedCepikDate).isEqualTo(expectedCepikDate);
  }

  private static Stream<Arguments> provideLocalDateAndCepikDate() {
    return Stream.of(
        Arguments.of(null, StringUtils.EMPTY),
        Arguments.of(LocalDate.of(2021, 10, 15), "20211015")
    );
  }

  @Test
  void shouldMapPolishDistrictNameToCode() {
    //given
    var districtName = "mazowieckie";
    var expectedCode = 14;

    //when
    int actualCode = mapperMethods.districtCodeMapper(districtName);

    //then
    assertThat(actualCode).isEqualTo(expectedCode);
  }

  @Test
  void shouldThrowExceptionBecauseWrongPolishDistrictName() {
    //given
    var invalidDistrictName = "AAA";

    //whenThen
    assertThatThrownBy(() -> mapperMethods.districtCodeMapper(invalidDistrictName))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Polish district name not correct");
  }
}
