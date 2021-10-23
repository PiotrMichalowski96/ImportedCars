package com.piter.importedcars.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ImportedCarsReport {
  private SearchParameters searchParameters;
  private List<Car> carList;
}
