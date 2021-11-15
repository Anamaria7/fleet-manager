package com.flixbus.fleetmanager.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NumberOperation {

  private Integer value;
  private OperationType operation;

}
