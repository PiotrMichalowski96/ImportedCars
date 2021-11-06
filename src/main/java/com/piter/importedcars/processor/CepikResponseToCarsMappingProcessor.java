package com.piter.importedcars.processor;

import com.piter.importedcars.exception.CepikResponseException;
import com.piter.importedcars.mapper.CepikResponseToCarMapper;
import com.piter.importedcars.model.Car;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;
import pl.cepik.model.JsonApiForListVehicle;

@Component
@RequiredArgsConstructor
public class CepikResponseToCarsMappingProcessor {

  private final CepikResponseToCarMapper cepikResponseToCarMapper;

  @Handler
  public List<Car> process(List<JsonApiForListVehicle> jsonApiForListVehicleList) {

    List<Car> carList = jsonApiForListVehicleList.stream()
        .map(JsonApiForListVehicle::getData)
        .filter(Objects::nonNull)
        .filter(Predicate.not(List::isEmpty))
        .map(cepikResponseToCarMapper::toCarList)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

    if(carList.isEmpty()) {
      throw new CepikResponseException("Cepik respond with empty car list");
    }
    return carList;
  }
}
