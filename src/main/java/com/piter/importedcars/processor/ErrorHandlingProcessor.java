package com.piter.importedcars.processor;

import com.piter.importedcars.exception.ErrorResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandlingProcessor {

  @Handler
  public ErrorResponse process(Exchange exchange) {
    Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
    String exceptionType = cause.getClass().getSimpleName();
    String message = cause.getMessage();
    return new ErrorResponse(exceptionType, message);
  }
}
