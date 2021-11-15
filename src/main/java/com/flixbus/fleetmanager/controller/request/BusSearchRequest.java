package com.flixbus.fleetmanager.controller.request;

import com.flixbus.fleetmanager.model.BusColor;
import com.flixbus.fleetmanager.model.BusType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusSearchRequest {

  private String plateNumber;
  private BusType type;
  private BusColor color;
//  private Pair<Integer, OperationType> capacity;
//  private NumberOperation capacity;
  private Integer capacity;
  private OperationType operation;
  private String depotName;

}
