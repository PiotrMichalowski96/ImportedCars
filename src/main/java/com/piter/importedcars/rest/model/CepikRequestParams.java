package com.piter.importedcars.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CepikRequestParams {
  private int districtCode;
  private String cepikRequestDateFrom;
}
