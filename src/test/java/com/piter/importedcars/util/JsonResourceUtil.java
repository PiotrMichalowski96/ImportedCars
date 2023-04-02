package com.piter.importedcars.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

@UtilityClass
public class JsonResourceUtil {

  private static final String SAMPLE_DIRECTORY = "samples/";

  private static final ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static String readFileAsString(String fileName) throws IOException {
    var pathResource = new ClassPathResource(SAMPLE_DIRECTORY + fileName);
    return IOUtils.toString(pathResource.getInputStream(), StandardCharsets.UTF_8);
  }

  public static <T> String convertToJson(T object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  public static <T> T convertToObject(Class<T> clazz, String fileName) throws IOException {
    var pathResource = new ClassPathResource(SAMPLE_DIRECTORY + fileName);
    return objectMapper.readValue(pathResource.getFile(), clazz);
  }
}
