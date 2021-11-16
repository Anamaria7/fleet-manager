package com.flixbus.fleetmanager.controller;

import com.flixbus.fleetmanager.error.ErrorInfo;
import com.flixbus.fleetmanager.error.ErrorInfoService;
import com.flixbus.fleetmanager.error.ServerToClientException;
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
  public ErrorInfo handleServletRequestReaderIOException(ServerToClientException ex) {
    return errorInfoService.createClientErrorInfo(ex);
  }

}
