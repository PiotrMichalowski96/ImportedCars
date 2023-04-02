package com.piter.importedcars.exception;

import lombok.Value;

@Value
public class ErrorResponse {
  String exceptionType;
  String message;
}
