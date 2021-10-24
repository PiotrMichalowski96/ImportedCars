package com.piter.importedcars.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import pl.cepik.model.JsonApiForListVehicle;

@UtilityClass
public class JsonResourceUtil {

  public static JsonApiForListVehicle parseInputJsonFile(String fileName) throws IOException {
    String pathToJson = String.format("samples/%s", fileName);
    Resource pathResource = new ClassPathResource(pathToJson);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper.readValue(pathResource.getFile(), JsonApiForListVehicle.class);
  }
}
