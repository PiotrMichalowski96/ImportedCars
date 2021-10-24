package com.piter.importedcars.processor;

import static com.piter.importedcars.processor.SettingSearchPropertiesProcessor.SEARCH_PROPERTIES;

import com.piter.importedcars.model.Car;
import com.piter.importedcars.model.ImportedCarsReport;
import com.piter.importedcars.model.SearchParameters;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

@Component
public class ImportCarsReportProcessor {

  @Handler
  public ImportedCarsReport process(Exchange exchange) {
    SearchParameters searchParams = exchange.getProperty(SEARCH_PROPERTIES, SearchParameters.class);
    List<Car> carList = exchange.getIn().getBody(List.class);
    return new ImportedCarsReport(searchParams, carList);
  }
}
