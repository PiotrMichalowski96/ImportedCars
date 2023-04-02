package com.piter.importedcars.processor;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.importedcars.exception.CarReportException;
import com.piter.importedcars.exception.ErrorResponse;
import com.piter.importedcars.util.ExchangeUtils;
import org.junit.jupiter.api.Test;

class ErrorHandlingProcessorTest {

  private final ErrorHandlingProcessor errorHandlingProcessor = new ErrorHandlingProcessor();

  @Test
  void shouldCreateErrorResponse() {
    //given
    var message = "Car report is wrong";
    var exception = new CarReportException(message);
    var exchange = ExchangeUtils.createExchangeWithException(exception);
    var expectedErrorResponse = new ErrorResponse(CarReportException.class.getSimpleName(), message);

    //when
    var actualErrorResponse = errorHandlingProcessor.process(exchange);

    //then
    assertThat(actualErrorResponse).isEqualTo(expectedErrorResponse);
  }
}