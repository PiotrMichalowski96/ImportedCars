package com.piter.importedcars.processor;

import static com.piter.importedcars.processor.SettingSearchPropertiesProcessor.SEARCH_PROPERTIES;

import com.piter.importedcars.exception.CarReportException;
import com.piter.importedcars.model.Car;
import com.piter.importedcars.model.ImportedCarsReport;
import com.piter.importedcars.model.SearchParameters;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ImportCarsReportProcessor {

  public static final String REPORT_NAME = "reportName";

  @Handler
  public ImportedCarsReport process(Exchange exchange) {
    SearchParameters searchParams = exchange.getProperty(SEARCH_PROPERTIES, SearchParameters.class);
    List<Car> carList = exchange.getIn().getBody(List.class);

    String carBrandAsFileName = retrieveCarBrandFrom(carList);
    exchange.setProperty(REPORT_NAME, carBrandAsFileName);

    return new ImportedCarsReport(searchParams, carList);
  }

  private String retrieveCarBrandFrom(List<Car> carList) {
    String carBrand = Optional.ofNullable(carList)
        .orElse(Collections.emptyList())
        .stream()
        .filter(Objects::nonNull)
        .map(Car::getBrand)
        .filter(StringUtils::isNotBlank)
        .findFirst()
        .orElseThrow(() -> new CarReportException("Empty car brand for report"));

    sanityCheckForCarBrand(carList, carBrand);

    return carBrand;
  }

  private void sanityCheckForCarBrand(List<Car> carList, String carBrand) {
    carList.stream()
        .map(Car::getBrand)
        .filter(brand -> !StringUtils.equals(brand, carBrand))
        .findAny()
        .ifPresent(brand -> {
          String errorMessage = String.format("Contains different car brands %s and %s", brand, carBrand);
          throw new CarReportException(errorMessage);
        });
  }
}
