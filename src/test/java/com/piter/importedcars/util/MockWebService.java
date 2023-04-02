package com.piter.importedcars.util;

import static com.piter.importedcars.util.JsonResourceUtil.convertToObject;
import static org.mockito.Mockito.when;

import com.piter.importedcars.rest.config.CepikWebserviceConfig;
import com.piter.importedcars.rest.model.CepikRequestParams;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.cepik.model.JsonApiForListVehicle;

@RequiredArgsConstructor
public class MockWebService {

  private final RestTemplate restTemplate;
  private final CepikWebserviceConfig cepikWebserviceConfig;

  public void mockOneApiCall(CepikRequestParams cepikRequestParams, String responseFilePath)
      throws IOException {

    JsonApiForListVehicle responseWithNullLastLink = convertToObject(JsonApiForListVehicle.class, responseFilePath);
    responseWithNullLastLink.getLinks().setLast(null);
    mockResponse(1, cepikRequestParams, responseWithNullLastLink);
  }

  public void mockTwoApiCalls(CepikRequestParams cepikRequestParams, String firstResponseFilePath,
      String secondResponseFilePath) throws IOException {

    JsonApiForListVehicle firstPageResponse = convertToObject(JsonApiForListVehicle.class, firstResponseFilePath);
    firstPageResponse.getLinks().setLast("cepikUrl?page=2");
    mockResponse(1, cepikRequestParams, firstPageResponse);

    JsonApiForListVehicle secondPageResponse = convertToObject(JsonApiForListVehicle.class, secondResponseFilePath);
    mockResponse(2, cepikRequestParams, secondPageResponse);
  }

  private void mockResponse(int pageNumber, CepikRequestParams cepikRequestParams,
      JsonApiForListVehicle response) {

    when(restTemplate.exchange(
            cepikWebserviceConfig.getUrl(),
            HttpMethod.GET,
            null,
            JsonApiForListVehicle.class,
            cepikRequestParams.getDistrictCode(),
            cepikRequestParams.getCepikRequestDateFrom(),
            pageNumber
        )
    ).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
  }
}
