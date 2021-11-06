package com.piter.importedcars.processor;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.importedcars.mapper.SearchParamsToCepikParamsMapperImpl;
import com.piter.importedcars.model.SearchParameters;
import com.piter.importedcars.rest.CepikRequestParams;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SearchParamsToCepikParamsMappingProcessorTest {

  private final SearchParamsToCepikParamsMappingProcessor processor = new SearchParamsToCepikParamsMappingProcessor(
      new SearchParamsToCepikParamsMapperImpl()
  );

  @Test
  public void shouldMapSearchParamsToCepikParams() {
    //given
    LocalDate searchFromDate = LocalDate.of(2021, 10, 31);
    SearchParameters searchParameters = new SearchParameters("mazowieckie", searchFromDate, List.of("Audi"));
    CepikRequestParams expectedCepikRequestParams = new CepikRequestParams(14, "20211031");

    //when
    CepikRequestParams actualCepikRequestParams = processor.process(searchParameters);

    //then
    assertThat(actualCepikRequestParams)
        .usingRecursiveComparison()
        .isEqualTo(expectedCepikRequestParams);
  }
}
