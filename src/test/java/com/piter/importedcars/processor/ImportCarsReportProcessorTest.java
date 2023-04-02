package com.piter.importedcars.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.piter.importedcars.exception.CarReportException;
import com.piter.importedcars.model.Car;
import com.piter.importedcars.model.SearchParameters;
import com.piter.importedcars.util.ExchangeUtils;
import java.time.LocalDate;
import java.util.List;
import org.apache.camel.Exchange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ImportCarsReportProcessorTest {

  private final ImportCarsReportProcessor processor = new ImportCarsReportProcessor();

  @Test
  void shouldSetReportNameProperty() {
    //given
    List<Car> carList = List.of(
        Car.builder()
            .brand("Audi")
            .model("A5")
            .firstRegistrationDate(LocalDate.now())
            .build(),
        Car.builder()
            .brand("Audi")
            .model("A6")
            .firstRegistrationDate(LocalDate.now())
            .build(),
        Car.builder()
            .brand("Audi")
            .model("A7")
            .firstRegistrationDate(LocalDate.now())
            .build()
    );

    Exchange exchange = createExchangeWithSearchParameter(carList);

    var expectedReportName = "Audi";

    //when
    processor.process(exchange);
    String actualReportName = exchange.getProperty(ImportCarsReportProcessor.REPORT_NAME,
        String.class);

    //then
    assertThat(actualReportName).isEqualTo(expectedReportName);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionBecauseCarListIsEmpty(List<Car> carList) {
    //given
    Exchange exchange = createExchangeWithSearchParameter(carList);

    //whenThen
    assertThatThrownBy(() -> processor.process(exchange))
        .isInstanceOf(CarReportException.class);
  }

  @Test
  void shouldThrowExceptionBecauseCarListContainsDifferentCarBrands() {
    //given
    List<Car> carList = List.of(
        Car.builder()
            .brand("Audi")
            .model("A5")
            .firstRegistrationDate(LocalDate.now())
            .build(),
        Car.builder()
            .brand("BMW")
            .model("X5")
            .firstRegistrationDate(LocalDate.now())
            .build(),
        Car.builder()
            .brand("Audi")
            .model("A7")
            .firstRegistrationDate(LocalDate.now())
            .build()
    );

    Exchange exchange = createExchangeWithSearchParameter(carList);

    var expectedErrorMessage = "Contains different car brands BMW and Audi";

    //whenThen
    assertThatThrownBy(() -> processor.process(exchange))
        .isInstanceOf(CarReportException.class)
        .hasMessage(expectedErrorMessage);
  }

  private Exchange createExchangeWithSearchParameter(List<Car> carList) {
    SearchParameters searchParameters = SearchParameters.builder()
        .district("podlaskie")
        .searchFromDate(LocalDate.now())
        .carBrandList(List.of("Audi", "Mercedes"))
        .build();
    return ExchangeUtils.createExchange(carList, searchParameters);
  }
}
