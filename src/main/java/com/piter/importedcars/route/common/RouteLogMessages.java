package com.piter.importedcars.route.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RouteLogMessages {

  public static final String STEP_START_BASE_ROUTE = "Start Imported Cars route";
  public static final String STEP_UNMARSHALLING = "Unmarshalling";
  public static final String STEP_VALIDATION = "Successful validation";
  public static final String STEP_SETTING_SEARCH_PROPERTIES = "Setting search property";
  public static final String STEP_MAPPING_SEARCH_PARAMS = "Mapping search params to cepik params";
  public static final String STEP_REST_INVOCATION = "Rest invocation of Cepik webservice";
  public static final String STEP_MAPPING_CEPIK_RESPONSE = "Mapping cepik response to car objects";
  public static final String STEP_CREATING_CARS_REPORT = "Creating import cars report";
  public static final String STEP_MARSHALLING_CARS_REPORT = "Marshalling cars report to json";
  public static final String STEP_ERROR_HANDLING = "Error handling";
  public static final String STEP_MARSHALLING_ERROR_RESPONSE = "Marshalling error response";
  public static final String STOP_PROCESSING_NO_SEARCH_PARAMS = "STOP PROCESSING: Retrieved car list doesnt contain search parameters: ${exchangeProperty.searchProperties.carBrand}";


  public static String stepDoneMessage(String message) {
    StringBuilder sb = new StringBuilder();
    sb.append("STEP DONE: ");
    sb.append(message);
    return sb.toString();
  }
}
