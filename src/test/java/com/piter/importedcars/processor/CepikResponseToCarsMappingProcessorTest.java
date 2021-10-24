package com.piter.importedcars.processor;

import static com.piter.importedcars.util.JsonResourceUtil.parseInputJsonFile;
import static org.assertj.core.api.Assertions.assertThat;

import com.piter.importedcars.mapper.CepikResponseToCarMapperImpl;
import com.piter.importedcars.mapper.MapperMethods;
import com.piter.importedcars.model.Car;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import pl.cepik.model.JsonApiForListVehicle;
import pl.cepik.model.VehicleDto;

public class CepikResponseToCarsMappingProcessorTest {

  private final CepikResponseToCarsMappingProcessor processor = new CepikResponseToCarsMappingProcessor(
      new CepikResponseToCarMapperImpl()
  );

  private final MapperMethods mapperMethods = new MapperMethods() {};

  @Test
  public void shouldMapCepikResponseToCars() throws IOException {
    //given
    JsonApiForListVehicle cepikResponse1 = parseInputJsonFile("CepikResponse_1.json");
    JsonApiForListVehicle cepikResponse2 = parseInputJsonFile("CepikResponse_2.json");
    List<JsonApiForListVehicle> jsonApiForListVehicleList = List.of(cepikResponse1, cepikResponse2);

    //when
    List<Car> carList = processor.process(jsonApiForListVehicleList);

    //then
    assertCarIsEqualToVehicleDto(carList.get(0), cepikResponse1.getData().get(0).getAttributes());
    assertCarIsEqualToVehicleDto(carList.get(1), cepikResponse1.getData().get(1).getAttributes());

    assertCarIsEqualToVehicleDto(carList.get(2), cepikResponse2.getData().get(0).getAttributes());
    assertCarIsEqualToVehicleDto(carList.get(3), cepikResponse2.getData().get(1).getAttributes());
    assertCarIsEqualToVehicleDto(carList.get(4), cepikResponse2.getData().get(2).getAttributes());
  }

  private void assertCarIsEqualToVehicleDto(Car car, VehicleDto vehicleDto) {
    String firstRegistrationCepikDate = vehicleDto.getDataOstatniejRejestracjiWKraju();
    LocalDate firstRegistrationDate = mapperMethods.cepikDateToLocalDateMapper(firstRegistrationCepikDate);

    assertThat(car.getFirstRegistrationDate()).isEqualTo(firstRegistrationDate);
    assertThat(car.getBrand()).isEqualTo(vehicleDto.getMarka());
    assertThat(car.getModel()).isEqualTo(vehicleDto.getModel());
  }
}
