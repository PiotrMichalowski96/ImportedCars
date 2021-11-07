package com.piter.importedcars.util;

import com.piter.importedcars.model.SearchParameters;
import com.piter.importedcars.processor.SettingSearchPropertiesProcessor;
import lombok.experimental.UtilityClass;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;

@UtilityClass
public class ExchangeUtils {

  public static <T> Exchange createExchange(T body) {
    CamelContext camelContext = new DefaultCamelContext();
    Exchange exchange = new DefaultExchange(camelContext);
    exchange.getIn().setBody(body);
    return exchange;
  }

  public static <T> Exchange createExchange(T body, SearchParameters searchParameters) {
    Exchange exchange = createExchange(body);
    exchange.setProperty(SettingSearchPropertiesProcessor.SEARCH_PROPERTIES, searchParameters);
    return exchange;
  }
}
