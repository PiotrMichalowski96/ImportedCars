package com.piter.importedcars.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
  private final String exceptionType;
  private final String message;
}
