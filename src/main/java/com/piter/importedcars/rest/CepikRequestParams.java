package com.piter.importedcars.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CepikRequestParams {
  private int districtCode;
  private String cepikRequestDateFrom;
}
