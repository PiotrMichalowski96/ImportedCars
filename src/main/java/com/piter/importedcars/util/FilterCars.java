package com.piter.importedcars.util;

import static com.piter.importedcars.processor.SettingSearchPropertiesProcessor.SEARCH_PROPERTIES;

import com.piter.importedcars.model.Car;
import com.piter.importedcars.model.SearchParameters;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.apache.camel.Predicate;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class FilterCars {

  public static Predicate doesNotContainSearchCarBrands() {
    return exchange -> {
      SearchParameters searchParameters = exchange.getProperty(SEARCH_PROPERTIES, SearchParameters.class);
      List<Car> cars = exchange.getIn().getBody(List.class);
      boolean containSearchParams = cars.stream()
          .map(Car::getBrand)
          .filter(Objects::nonNull)
          .anyMatch(brand ->  searchParameters.getCarBrandList().stream()
              .anyMatch(searchCarBrands -> StringUtils.equalsIgnoreCase(searchCarBrands, brand)));
      return !containSearchParams;
    };
  }

  public static Predicate filterCarsByBrand() {
    return exchange -> {
      SearchParameters searchParameters = exchange.getProperty(SEARCH_PROPERTIES, SearchParameters.class);
      Car car = exchange.getIn().getBody(Car.class);
      return searchParameters.getCarBrandList().stream()
          .anyMatch(searchCarBrands -> StringUtils.equalsIgnoreCase(searchCarBrands, car.getBrand()));
    };
  }
}
