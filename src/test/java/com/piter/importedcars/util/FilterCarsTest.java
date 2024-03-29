package com.piter.importedcars.util;

import static com.piter.importedcars.util.ExchangeUtils.createExchange;
import static org.assertj.core.api.Assertions.assertThat;

import com.piter.importedcars.model.Car;
import com.piter.importedcars.model.SearchParameters;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.apache.camel.Exchange;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FilterCarsTest {

  @ParameterizedTest
  @MethodSource("provideCarsAndSearchCarBrandsList")
  void shouldCheckIfContainsSearchCarBrands(List<Car> carList, List<String> carBrandList,
      boolean expectedResult) {
    //given
    var searchParameters = SearchParameters.builder()
        .district("podlaskie")
        .searchFromDate(LocalDate.now())
        .carBrandList(carBrandList)
        .build();

    Exchange exchange = createExchange(carList, searchParameters);

    //when
    boolean actualResult = FilterCars.doesNotContainSearchCarBrands().matches(exchange);

    //then
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  private static Stream<Arguments> provideCarsAndSearchCarBrandsList() {
    List<Car> carList = List.of(
        Car.builder()
            .brand("Audi")
            .model("A5")
            .firstRegistrationDate(LocalDate.now())
            .build(),
        Car.builder()
            .brand("BMW")
            .model("M5")
            .firstRegistrationDate(LocalDate.now())
            .build(),
        Car.builder()
            .brand("Audi")
            .model("A7")
            .firstRegistrationDate(LocalDate.now())
            .build()
    );

    return Stream.of(
        Arguments.of(carList, List.of("Mercedes", "Audi"), false),
        Arguments.of(carList, List.of("bmw"), false),
        Arguments.of(carList, List.of("Ford", "Mercedes"), true)
    );
  }

  @ParameterizedTest
  @MethodSource("provideOneCarAndSearchCarBrandsList")
  void shouldCheckIfCarHasSearchedBrand(Car car, List<String> carBrandList,
      boolean expectedResult) {
    //given
    var searchParameters = SearchParameters.builder()
        .district("podlaskie")
        .searchFromDate(LocalDate.now())
        .carBrandList(carBrandList)
        .build();

    Exchange exchange = createExchange(car, searchParameters);

    //when
    boolean actualResult = FilterCars.filterCarsByBrand().matches(exchange);

    //then
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  private static Stream<Arguments> provideOneCarAndSearchCarBrandsList() {
    var car = Car.builder()
        .brand("Audi")
        .model("A4")
        .firstRegistrationDate(LocalDate.now())
        .build();

    return Stream.of(
        Arguments.of(car, List.of("Mercedes", "Audi"), true),
        Arguments.of(car, List.of("audi"), true),
        Arguments.of(car, List.of("Ford", "Mercedes"), false)
    );
  }
}
