package com.piter.importedcars.processor;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.importedcars.mapper.SearchParamsToCepikParamsMapperImpl;
import com.piter.importedcars.model.SearchParameters;
import com.piter.importedcars.rest.model.CepikRequestParams;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class SearchParamsToCepikParamsMappingProcessorTest {

  private final SearchParamsToCepikParamsMappingProcessor processor = new SearchParamsToCepikParamsMappingProcessor(
      new SearchParamsToCepikParamsMapperImpl()
  );

  @Test
  void shouldMapSearchParamsToCepikParams() {
    //given
    var searchFromDate = LocalDate.of(2021, 10, 31);
    var searchParameters = SearchParameters.builder()
        .district("mazowieckie")
        .searchFromDate(searchFromDate)
        .carBrandList(List.of("Audi"))
        .build();
    var expectedCepikRequestParams = new CepikRequestParams(14, "20211031");

    //when
    CepikRequestParams actualCepikRequestParams = processor.process(searchParameters);

    //then
    assertThat(actualCepikRequestParams)
        .usingRecursiveComparison()
        .isEqualTo(expectedCepikRequestParams);
  }
}
