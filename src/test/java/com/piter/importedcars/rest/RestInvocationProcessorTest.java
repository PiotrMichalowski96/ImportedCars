package com.piter.importedcars.rest;

import static com.piter.importedcars.util.JsonResourceUtil.parseInputJsonFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.piter.importedcars.rest.config.CepikWebserviceConfig;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.cepik.model.JsonApiForListVehicle;

@ExtendWith(MockitoExtension.class)
class RestInvocationProcessorTest {

  @Mock
  private RestTemplate restTemplate;
  @Mock
  private CepikWebserviceConfig cepikWebserviceConfig;

  private RestInvocationProcessor processor;

  @BeforeEach
  void setUp() {
    processor = new RestInvocationProcessor(restTemplate, cepikWebserviceConfig);
  }

  @Test
  void shouldInvokeCepikWebServiceAndGetOneResponse() throws IOException {
    //given
    String cepikUrl = "cepikUrl";
    CepikRequestParams cepikRequestParams = new CepikRequestParams(14, "20211015");

    JsonApiForListVehicle responseWithNullLastLink = parseInputJsonFile("CepikResponse_1.json");
    responseWithNullLastLink.getLinks().setLast(null);

    when(cepikWebserviceConfig.getUrl()).thenReturn(cepikUrl);
    mockWebServiceResponse(cepikUrl, 1, cepikRequestParams, responseWithNullLastLink);

    List<JsonApiForListVehicle> expectedResponseList = List.of(responseWithNullLastLink);

    //when
    List<JsonApiForListVehicle> responseList = processor.process(cepikRequestParams);

    //then
    assertThat(responseList)
        .hasSize(1)
        .hasSameElementsAs(expectedResponseList);
  }

  @Test
  void shouldInvokeCepikWebServiceAndGetTwoResponse() throws IOException {
    //given
    String cepikUrl = "cepikUrl";
    CepikRequestParams cepikRequestParams = new CepikRequestParams(14, "20211015");

    JsonApiForListVehicle firstPageResponse = parseInputJsonFile("CepikResponse_1.json");
    firstPageResponse.getLinks().setLast("cepikUrl?page=2");

    when(cepikWebserviceConfig.getUrl()).thenReturn(cepikUrl);
    mockWebServiceResponse(cepikUrl, 1, cepikRequestParams, firstPageResponse);

    JsonApiForListVehicle secondPageResponse = parseInputJsonFile("CepikResponse_2.json");
    mockWebServiceResponse(cepikUrl, 2, cepikRequestParams, secondPageResponse);

    List<JsonApiForListVehicle> expectedResponseList = List.of(firstPageResponse, secondPageResponse);

    //when
    List<JsonApiForListVehicle> responseList = processor.process(cepikRequestParams);

    //then
    assertThat(responseList)
        .hasSize(2)
        .hasSameElementsAs(expectedResponseList);
  }

  private void mockWebServiceResponse(String url, int pageNumber,
      CepikRequestParams cepikRequestParams, JsonApiForListVehicle response) {

    when(restTemplate.exchange(url, HttpMethod.GET,
        null, JsonApiForListVehicle.class, cepikRequestParams.getDistrictCode(),
        cepikRequestParams.getCepikRequestDateFrom(), pageNumber))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
  }
}
