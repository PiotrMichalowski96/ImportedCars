package com.piter.importedcars.processor;

import com.piter.importedcars.model.SearchParameters;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SettingSearchPropertiesProcessor implements Processor {

  public static final String SEARCH_PROPERTIES = "searchProperties";

  @Override
  public void process(Exchange exchange) throws Exception {
    SearchParameters searchParameters = exchange.getIn().getBody(SearchParameters.class);
    exchange.setProperty(SEARCH_PROPERTIES, searchParameters);
  }
}
