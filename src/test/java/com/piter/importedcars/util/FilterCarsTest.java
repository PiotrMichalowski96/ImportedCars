package com.piter.importedcars.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.importedcars.model.Car;
import com.piter.importedcars.model.SearchParameters;
import com.piter.importedcars.processor.SettingSearchPropertiesProcessor;
import java.time.LocalDate;
import java.util.List;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FilterCarsTest {

  @ParameterizedTest
  @CsvSource({"Audi,false", "Mercedes,true"})
  public void shouldCheckIfContainsSearchCarBrands(String searchCarBrand, boolean expectedResult) {
    //given
    List<Car> carList = List.of(
        new Car("Audi", "A5", LocalDate.now()),
        new Car("BMW", "M5", LocalDate.now()),
        new Car("Audi", "A7", LocalDate.now())
    );

    SearchParameters searchParameters = new SearchParameters("podlaskie", LocalDate.now(), searchCarBrand);

    Exchange exchange = createExchange(carList, searchParameters);

    //when
    boolean actualResult = FilterCars.doesNotContainSearchCarBrands().matches(exchange);

    //then
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @ParameterizedTest
  @CsvSource({"Audi,true", "Mercedes,false"})
  public void shouldCheckIfCarHasSearchedBrand(String carBrand, boolean expectedResult) {
    //given
    Car car = new Car("Audi", "A4", LocalDate.now());

    SearchParameters searchParameters = new SearchParameters("podlaskie", LocalDate.now(), carBrand);

    Exchange exchange = createExchange(car, searchParameters);

    //when
    boolean actualResult = FilterCars.filterCarsByBrand().matches(exchange);

    //then
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  private <T> Exchange createExchange(T body, SearchParameters searchParameters) {
    CamelContext camelContext = new DefaultCamelContext();
    Exchange exchange = new DefaultExchange(camelContext);
    exchange.setProperty(SettingSearchPropertiesProcessor.SEARCH_PROPERTIES, searchParameters);
    exchange.getIn().setBody(body);
    return exchange;
  }
}
