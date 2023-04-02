package com.piter.importedcars.route;

import static com.piter.importedcars.util.JsonResourceUtil.readFileAsString;

import com.piter.importedcars.rest.config.CepikWebserviceConfig;
import com.piter.importedcars.rest.model.CepikRequestParams;
import com.piter.importedcars.util.ExchangeUtils;
import com.piter.importedcars.util.MockWebService;
import java.io.IOException;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@CamelSpringBootTest
@SpringBootTest(properties = {"route.input=direct:input", "route.output=mock:output"})
class ImportedCarsRouteTest {

  private static final String INPUT_SEARCH_PARAMS = "SearchParams.json";
  private static final String OUTPUT_REPORT = "CarReport-PEUGEOT.json";
  private static final String FIRST_CEPIK_RESPONSE = "CepikResponse_1.json";
  private static final String SECOND_CEPIK_RESPONSE = "CepikResponse_2.json";

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private CepikWebserviceConfig cepikWebserviceConfig;

  @Autowired
  private ProducerTemplate producerTemplate;

  @EndpointInject("mock:output")
  private MockEndpoint mockOutputEndpoint;

  private MockWebService mockCepikWebService;

  @BeforeEach
  void init() {
    mockCepikWebService = new MockWebService(restTemplate, cepikWebserviceConfig);
  }

  @Test
  void shouldCorrectlyProcessAndSendImportedCarsReport() throws IOException, InterruptedException {
    //given
    var inputMessage = readFileAsString(INPUT_SEARCH_PARAMS);
    var exchange = ExchangeUtils.createExchange(inputMessage);

    var cepikRequestParams = new CepikRequestParams(14, "20230401");
    mockCepikWebService.mockTwoApiCalls(cepikRequestParams, FIRST_CEPIK_RESPONSE, SECOND_CEPIK_RESPONSE);

    var expectedReport = readFileAsString(OUTPUT_REPORT);
    mockOutputEndpoint.expectedMessageCount(1);
    mockOutputEndpoint.expectedBodiesReceived(expectedReport);

    //when
    producerTemplate.send("direct:input", exchange);

    //then
    mockOutputEndpoint.assertIsSatisfied();
  }
}