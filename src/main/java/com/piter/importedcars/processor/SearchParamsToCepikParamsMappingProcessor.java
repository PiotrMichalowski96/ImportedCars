package com.piter.importedcars.processor;

import com.piter.importedcars.mapper.SearchParamsToCepikParamsMapper;
import com.piter.importedcars.model.SearchParameters;
import com.piter.importedcars.rest.CepikRequestParams;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchParamsToCepikParamsMappingProcessor {

  private final SearchParamsToCepikParamsMapper searchParamsToCepikParamsMapper;

  @Handler
  public CepikRequestParams process(SearchParameters searchParameters) {
    return searchParamsToCepikParamsMapper.toCepikRequestParams(searchParameters);
  }
}
