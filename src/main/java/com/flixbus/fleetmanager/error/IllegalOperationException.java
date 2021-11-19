package com.flixbus.fleetmanager.error;

import lombok.Getter;

@Getter
public class IllegalOperationException extends ServerToClientException {

  public IllegalOperationException(String message) {
    super(message);
  }
}
