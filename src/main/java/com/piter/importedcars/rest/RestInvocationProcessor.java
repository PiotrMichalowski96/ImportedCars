package com.piter.importedcars.rest;

import com.piter.importedcars.rest.config.CepikWebserviceConfig;
import com.piter.importedcars.rest.model.CepikRequestParams;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.cepik.model.ApiLinksDto;
import pl.cepik.model.JsonApiForListVehicle;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestInvocationProcessor {

  private final RestTemplate restTemplate;
  private final CepikWebserviceConfig cepikWebserviceConfig;

  @Handler
  public List<JsonApiForListVehicle> process(CepikRequestParams cepikRequestParams) {

    int districtCode = cepikRequestParams.getDistrictCode();
    String requestDateFrom = cepikRequestParams.getCepikRequestDateFrom();
    String url = cepikWebserviceConfig.getUrl();

    ResponseEntity<JsonApiForListVehicle> responseFirstPage = restTemplate.exchange(url, HttpMethod.GET,
        null, JsonApiForListVehicle.class, districtCode, requestDateFrom, 1);

    int maxPageNumber = retrieveLastLink(responseFirstPage)
        .map(this::retrieveMaxPageNumber)
        .orElse(1);

    logger.info("Cepik webservice returns max page numbers: {}", maxPageNumber);

    return IntStream.rangeClosed(1, maxPageNumber)
        .peek(number -> logger.info("Calling Cepik webservice for page number: {}", number))
        .mapToObj(pageNumber -> restTemplate.exchange(url, HttpMethod.GET,null,
            JsonApiForListVehicle.class, districtCode, requestDateFrom, pageNumber))
        .map(ResponseEntity::getBody)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private Optional<String> retrieveLastLink(ResponseEntity<JsonApiForListVehicle> response) {
    return Optional.ofNullable(response)
        .map(ResponseEntity::getBody)
        .map(JsonApiForListVehicle::getLinks)
        .map(ApiLinksDto::getLast);
  }

  private Integer retrieveMaxPageNumber(String lastLink) {
    final String pageParamName = "page";
    MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUriString(lastLink)
        .build()
        .getQueryParams();

    return Optional.ofNullable(queryParams.getFirst(pageParamName))
        .map(Integer::valueOf)
        .orElse(1);
  }
}
