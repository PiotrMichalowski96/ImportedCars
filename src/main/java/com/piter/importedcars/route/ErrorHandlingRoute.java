package com.piter.importedcars.route;

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
  static final String ERROR_DIRECT_URI = "direct:error";

  @Value("${route.error}")
  private final String errorOutputUri;
  private final ErrorHandlingProcessor errorHandlingProcessor;

  @Override
  public void configure() throws Exception {

    from(ERROR_DIRECT_URI)
        .routeId(ROUTE_ID)
        .setExchangePattern(ExchangePattern.InOnly)

        .bean(errorHandlingProcessor)
        .id("Error processing")
        .log(LoggingLevel.INFO, logger, "Error processing")

//        .marshal().json()
//        .id("Marshalling")
//        .log(LoggingLevel.INFO, logger, "Marshalling")

        .to(errorOutputUri);
  }

}
