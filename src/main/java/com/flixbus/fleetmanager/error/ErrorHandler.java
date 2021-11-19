package com.flixbus.fleetmanager.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

  private final ErrorInfoService errorInfoService;

  @Autowired
  public ErrorHandler(ErrorInfoService errorInfoService) {
    this.errorInfoService = errorInfoService;
  }

  @ExceptionHandler(ServerToClientException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorInfo handleServerToClientException(ServerToClientException ex) {
    return errorInfoService.createClientErrorInfo(ex);
  }

  @ExceptionHandler(IllegalOperationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorInfo handleIllegalOperationException(IllegalOperationException ex) {
    return errorInfoService.createClientErrorInfo(ex);
  }
}
