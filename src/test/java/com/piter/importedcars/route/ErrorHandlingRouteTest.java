package com.piter.importedcars.route;

import static com.piter.importedcars.util.JsonResourceUtil.convertToJson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.piter.importedcars.exception.CarReportException;
import com.piter.importedcars.exception.ErrorResponse;
import com.piter.importedcars.util.ExchangeUtils;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
@SpringBootTest(properties = {"route.error=mock:routeError"})
class ErrorHandlingRouteTest {

  @Autowired
  private ProducerTemplate producerTemplate;

  @EndpointInject("mock:routeError")
  private MockEndpoint mockEndpoint;

  @Test
  public void shouldHandleError() throws InterruptedException, JsonProcessingException {
    //given
    var message = "Car report contains error";
    var exception = new CarReportException(message);
    var exchange = ExchangeUtils.createExchangeWithException(exception);
    var errorResponse = new ErrorResponse(CarReportException.class.getSimpleName(), message);
    var expectedErrorJson = convertToJson(errorResponse);

    mockEndpoint.expectedMessageCount(1);
    mockEndpoint.expectedBodiesReceived(expectedErrorJson);

    //when
    producerTemplate.send(ErrorHandlingRoute.ERROR_DIRECT_URI, exchange);

    //then
    mockEndpoint.assertIsSatisfied();
  }
}