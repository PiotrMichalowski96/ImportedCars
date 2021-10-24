package com.piter.importedcars.route;

import static com.piter.importedcars.route.common.RouteLogMessages.*;
import static com.piter.importedcars.route.common.RouteLogMessages.stepDoneMessage;

import com.piter.importedcars.aggregation.CarCorrelationKey;
import com.piter.importedcars.model.SearchParameters;
import com.piter.importedcars.processor.CepikResponseToCarsMappingProcessor;
import com.piter.importedcars.processor.ImportCarsReportProcessor;
import com.piter.importedcars.util.FilterCars;
import com.piter.importedcars.processor.SearchParamsToCepikParamsMappingProcessor;
import com.piter.importedcars.processor.SettingSearchPropertiesProcessor;
import com.piter.importedcars.rest.RestInvocationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImportedCarsRoute extends RouteBuilder {

  private static final String ROUTE_ID = "imported-cars-route";

  @Value("${route.input}")
  private final String inputUri;
  @Value("${route.output}")
  private final String outputUri;
  @Value("${route.chunkSize}")
  private final int chunkSize;
  @Value("${route.timeout}")
  private final int completionTimeout;

  private final SettingSearchPropertiesProcessor settingSearchPropertiesProcessor;
  private final SearchParamsToCepikParamsMappingProcessor searchParamsToCepikParamsProcessor;
  private final RestInvocationProcessor restInvocationProcessor;
  private final CepikResponseToCarsMappingProcessor cepikResponseToCarsMappingProcessor;
  private final CarCorrelationKey carCorrelationKey;
  private final ImportCarsReportProcessor importCarsReportProcessor;

  @Override
  public void configure() throws Exception {

    errorHandler(deadLetterChannel(ErrorHandlingRoute.ERROR_DIRECT_URI));

    from(inputUri)
        .routeId(ROUTE_ID)
        .setExchangePattern(ExchangePattern.InOnly)
        .id(STEP_START_BASE_ROUTE)
        .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_START_BASE_ROUTE))

        .unmarshal().json(JsonLibrary.Jackson, SearchParameters.class)
        .id(STEP_UNMARSHALLING)
        .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_UNMARSHALLING))

        //TODO: input data validation

        .process(settingSearchPropertiesProcessor)
        .id(STEP_SETTING_SEARCH_PROPERTIES)
        .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_SETTING_SEARCH_PROPERTIES))

        .bean(searchParamsToCepikParamsProcessor)
        .id(STEP_MAPPING_SEARCH_PARAMS)
        .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_MAPPING_SEARCH_PARAMS))

        .bean(restInvocationProcessor)
        .id(STEP_REST_INVOCATION)
        .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_REST_INVOCATION))

        .bean(cepikResponseToCarsMappingProcessor)
        .id(STEP_MAPPING_CEPIK_RESPONSE)
        .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_MAPPING_CEPIK_RESPONSE))

        .split(body())

        .filter(FilterCars.filterCarsByBrand())

        //TODO: if empty send message

        .aggregate(carCorrelationKey, new GroupedBodyAggregationStrategy())
        .completionSize(chunkSize)
        .completionTimeout(completionTimeout)

        .bean(importCarsReportProcessor)
        .id(STEP_CREATING_CARS_REPORT)
        .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_CREATING_CARS_REPORT))

        .marshal().json()
        .id(STEP_MARSHALLING_CARS_REPORT)
        .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_MARSHALLING_CARS_REPORT))

        .to(outputUri);
  }
}
