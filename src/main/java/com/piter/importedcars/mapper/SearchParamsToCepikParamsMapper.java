package com.piter.importedcars.mapper;

import com.piter.importedcars.mapper.qualifier.DistrictCodeMapper;
import com.piter.importedcars.mapper.qualifier.LocalDateToCepikDateMapper;
import com.piter.importedcars.model.SearchParameters;
import com.piter.importedcars.rest.model.CepikRequestParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SearchParamsToCepikParamsMapper extends MapperMethods {

  @Mapping(target = "districtCode", source = "district", qualifiedBy = DistrictCodeMapper.class)
  @Mapping(target = "cepikRequestDateFrom", source = "searchFromDate", qualifiedBy = LocalDateToCepikDateMapper.class)
  CepikRequestParams toCepikRequestParams(SearchParameters searchParameters);
}
