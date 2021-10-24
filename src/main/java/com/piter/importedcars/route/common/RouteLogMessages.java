package com.piter.importedcars.route.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RouteLogMessages {
    public static final String STEP_START_BASE_ROUTE = "Start Imported Cars route";
    public static final String STEP_UNMARSHALLING = "Unmarshalling";
    public static final String STEP_SETTING_SEARCH_PROPERTIES = "Setting search property";
    public static final String STEP_MAPPING_SEARCH_PARAMS = "Mapping search params to cepik params";
    public static final String STEP_REST_INVOCATION = "Rest invocation of Cepik webservice";
    public static final String STEP_MAPPING_CEPIK_RESPONSE = "Mapping cepik response to car objects";
    public static final String STEP_CREATING_CARS_REPORT = "Creating import cars report";
    public static final String STEP_MARSHALLING_CARS_REPORT = "Marshalling cars report to json";

    public static String readFrom(String inputUri) {
        StringBuilder sb = new StringBuilder();
        sb.append("Read from: ");
        sb.append(inputUri);
        return sb.toString();
    }

    public static String stepDoneMessage(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("STEP DONE: ");
        sb.append(message);
        return sb.toString();
    }
}
