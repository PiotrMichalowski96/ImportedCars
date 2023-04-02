package com.piter.importedcars.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.piter.importedcars.exception.CarReportException;
import com.piter.importedcars.model.Car;
import com.piter.importedcars.model.SearchParameters;
import com.piter.importedcars.util.ExchangeUtils;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.apache.camel.Exchange;
import org.junit.jupiter.api.Test;

class ImportCarsReportProcessorTest {

  private final ImportCarsReportProcessor processor = new ImportCarsReportProcessor();

  @Test
  void shouldSetReportNameProperty() {
    //given
    List<Car> carList = List.of(
        new Car("Audi", "A5", LocalDate.now()),
        new Car("Audi", "A6", LocalDate.now()),
        new Car("Audi", "A7", LocalDate.now())
    );

    Exchange exchange = createExchangeWithSearchParameter(carList);

    String expectedReportName = "Audi";

    //when
    processor.process(exchange);
    String actualReportName = exchange.getProperty(ImportCarsReportProcessor.REPORT_NAME, String.class);

    //then
    assertThat(actualReportName).isEqualTo(expectedReportName);
  }

  @Test
  void shouldThrowExceptionBecauseCarListIsEmpty() {
    //given
    List<Car> carList = Collections.emptyList();

    Exchange exchange = createExchangeWithSearchParameter(carList);

    String expectedErrorMessage = "Empty car brand for report";

    //whenThen
    assertThatThrownBy(() -> processor.process(exchange))
        .isInstanceOf(CarReportException.class)
        .hasMessage(expectedErrorMessage);
  }

  @Test
  void shouldThrowExceptionBecauseCarListContainsDifferentCarBrands() {
    //given
    List<Car> carList = List.of(
        new Car("Audi", "A5", LocalDate.now()),
        new Car("BMW", "X5", LocalDate.now()),
        new Car("Audi", "A7", LocalDate.now())
    );

    Exchange exchange = createExchangeWithSearchParameter(carList);

    String expectedErrorMessage = "Contains different car brands BMW and Audi";

    //whenThen
    assertThatThrownBy(() -> processor.process(exchange))
        .isInstanceOf(CarReportException.class)
        .hasMessage(expectedErrorMessage);
  }

  private Exchange createExchangeWithSearchParameter(List<Car> carList) {
    SearchParameters searchParameters = new SearchParameters("podlaskie", LocalDate.now(),
        List.of("Audi", "Mercedes"));
    return ExchangeUtils.createExchange(carList, searchParameters);
  }
}
