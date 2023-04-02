package com.piter.importedcars.rest;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.importedcars.rest.config.CepikWebserviceConfig;
import com.piter.importedcars.rest.model.CepikRequestParams;
import com.piter.importedcars.util.MockWebService;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import pl.cepik.model.JsonApiForListVehicle;

@ExtendWith(MockitoExtension.class)
class RestInvocationProcessorTest {

  private static final String CEPIK_URL = "cepik-url";
  private static final String FIRST_RESPONSE_FILE = "CepikResponse_1.json";
  private static final String SECOND_RESPONSE_FILE = "CepikResponse_2.json";

  @Mock
  private RestTemplate restTemplate;

  private MockWebService mockCepikWebService;

  private RestInvocationProcessor processor;

  @BeforeEach
  void setUp() {
    CepikWebserviceConfig cepikWebserviceConfig = new CepikWebserviceConfig();
    cepikWebserviceConfig.setUrl(CEPIK_URL);
    processor = new RestInvocationProcessor(restTemplate, cepikWebserviceConfig);
    mockCepikWebService = new MockWebService(restTemplate, cepikWebserviceConfig);
  }

  @Test
  void shouldInvokeCepikWebServiceAndGetOneResponse() throws IOException {
    //given
    var cepikRequestParams = new CepikRequestParams(14, "20211015");
    mockCepikWebService.mockOneApiCall(cepikRequestParams, FIRST_RESPONSE_FILE);

    //when
    List<JsonApiForListVehicle> responseList = processor.process(cepikRequestParams);

    //then
    assertThat(responseList).hasSize(1);
  }

  @Test
  void shouldInvokeCepikWebServiceAndGetTwoResponse() throws IOException {
    //given
    var cepikRequestParams = new CepikRequestParams(14, "20211015");
    mockCepikWebService.mockTwoApiCalls(cepikRequestParams, FIRST_RESPONSE_FILE, SECOND_RESPONSE_FILE);

    //when
    List<JsonApiForListVehicle> responseList = processor.process(cepikRequestParams);

    //then
    assertThat(responseList).hasSize(2);
  }
}
