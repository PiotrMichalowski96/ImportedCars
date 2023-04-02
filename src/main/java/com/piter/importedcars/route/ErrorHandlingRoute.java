package com.piter.importedcars.route;

import static com.piter.importedcars.route.common.RouteLogMessages.STEP_ERROR_HANDLING;
import static com.piter.importedcars.route.common.RouteLogMessages.STEP_MARSHALLING_ERROR_RESPONSE;
import static com.piter.importedcars.route.common.RouteLogMessages.stepDoneMessage;

import com.piter.importedcars.processor.ErrorHandlingProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorHandlingRoute extends RouteBuilder {

  private static final String ROUTE_ID = "error-handling-route";
  static final String ERROR_DIRECT_URI = "direct:error-handling";

  @Value("${route.error}")
  private final String errorOutputUri;
  private final ErrorHandlingProcessor errorHandlingProcessor;

  @Override
  public void configure() {

    from(ERROR_DIRECT_URI)
        .routeId(ROUTE_ID)
        .setExchangePattern(ExchangePattern.InOnly)

        .bean(errorHandlingProcessor)
        .id(STEP_ERROR_HANDLING)
        .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_ERROR_HANDLING))

        .marshal().json()
        .id(STEP_MARSHALLING_ERROR_RESPONSE)
        .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_MARSHALLING_ERROR_RESPONSE))

        .to(errorOutputUri);
  }

}
