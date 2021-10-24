package com.piter.importedcars.processor;

import com.piter.importedcars.mapper.CepikResponseToCarMapper;
import com.piter.importedcars.model.Car;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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

    return jsonApiForListVehicleList.stream()
        .map(JsonApiForListVehicle::getData)
        .filter(Objects::nonNull)
        .map(cepikResponseToCarMapper::toCarList)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }
}
