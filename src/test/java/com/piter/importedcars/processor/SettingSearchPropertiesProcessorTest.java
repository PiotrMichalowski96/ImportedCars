package com.piter.importedcars.processor;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.importedcars.model.SearchParameters;
import com.piter.importedcars.util.ExchangeUtils;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class SettingSearchPropertiesProcessorTest {

  private final SettingSearchPropertiesProcessor processor = new SettingSearchPropertiesProcessor();

  @Test
  void shouldSetProperty() throws Exception {
    //given
    var searchParams = SearchParameters.builder()
        .searchFromDate(LocalDate.now())
        .carBrandList(List.of("Audi", "Mercedes"))
        .build();

    var exchange = ExchangeUtils.createExchange(searchParams);

    //when
    processor.process(exchange);
    SearchParameters actualSearchParams = exchange.getProperty(SettingSearchPropertiesProcessor.SEARCH_PROPERTIES, SearchParameters.class);

    //then
    assertThat(actualSearchParams).isEqualTo(searchParams);
  }
}