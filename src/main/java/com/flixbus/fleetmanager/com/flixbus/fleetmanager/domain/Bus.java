package com.flixbus.fleetmanager.com.flixbus.fleetmanager.domain;

import lombok.Data;

@Data
public class Bus {

  private String plateNumber;
  private BusType type;
  private BusColor color;
  private Integer capacity;

}
