package com.piter.importedcars.mapper;

import com.piter.importedcars.mapper.qualifier.CepikDateToLocalDateMapper;
import com.piter.importedcars.mapper.qualifier.DistrictCodeMapper;
import com.piter.importedcars.mapper.qualifier.LocalDateToCepikDateMapper;
import com.piter.importedcars.model.District;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.MapperConfig;

@MapperConfig
public interface MapperMethods {

  @CepikDateToLocalDateMapper
  default LocalDate cepikDateToLocalDateMapper(String cepikDate) {
    if(StringUtils.isBlank(cepikDate)) {
      return null;
    }
    return LocalDate.parse(cepikDate, DateTimeFormatter.ISO_LOCAL_DATE);
  }

  @LocalDateToCepikDateMapper
  default String localDateToCepikDateMapper(LocalDate localDate) {
    if(localDate == null) {
      return StringUtils.EMPTY;
    }
    return localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
  }

  @DistrictCodeMapper
  default int districtCodeMapper(String districtName) {
    return Stream.of(District.values())
        .filter(district -> StringUtils.equalsIgnoreCase(districtName, district.getPolishDistrictName()))
        .findFirst()
        .map(District::getPolishDistrictCode)
        .orElseThrow(() -> new IllegalArgumentException("Polish district name not correct"));
  }
}
