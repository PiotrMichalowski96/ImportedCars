package com.piter.importedcars.util;

import static com.piter.importedcars.processor.SettingSearchPropertiesProcessor.SEARCH_PROPERTIES;

import com.piter.importedcars.model.Car;
import com.piter.importedcars.model.SearchParameters;
import lombok.experimental.UtilityClass;
import org.apache.camel.Predicate;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class FilterCars {

  public static Predicate filterCarsByBrand() {
    return exchange -> {
      SearchParameters searchParameters = exchange.getProperty(SEARCH_PROPERTIES, SearchParameters.class);
      Car car = exchange.getIn().getBody(Car.class);
      return StringUtils.equalsIgnoreCase(car.getBrand(), searchParameters.getCarBrand());
    };
  }
}
