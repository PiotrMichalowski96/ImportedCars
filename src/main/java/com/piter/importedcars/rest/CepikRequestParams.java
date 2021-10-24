package com.piter.importedcars.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CepikRequestParams {
  private int districtCode;
  private String cepikRequestDateFrom;
}
