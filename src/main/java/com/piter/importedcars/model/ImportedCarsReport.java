package com.piter.importedcars.model;

import java.util.List;
import lombok.Value;

@Value
public class ImportedCarsReport {
  SearchParameters searchParameters;
  List<Car> carList;
}
