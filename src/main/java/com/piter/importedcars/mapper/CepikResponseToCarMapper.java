package com.piter.importedcars.mapper;

import com.piter.importedcars.mapper.qualifier.CepikDateToLocalDateMapper;
import com.piter.importedcars.model.Car;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.cepik.model.ApiAttributesDtoVehicle;

@Mapper(componentModel = "spring")
public interface CepikResponseToCarMapper extends MapperMethods {

  @Mapping(target = "brand", source = "attributes.marka")
  @Mapping(target = "model", source = "attributes.model")
  @Mapping(target = "firstRegistrationDate", source = "attributes.dataOstatniejRejestracjiWKraju", qualifiedBy = CepikDateToLocalDateMapper.class)
  Car toCar(ApiAttributesDtoVehicle attributesDtoVehicle);

  List<Car> toCarList(List<ApiAttributesDtoVehicle> apiAttributesDtoVehicleList);
}
