package com.flixbus.fleetmanager.com.flixbus.fleetmanager.domain;

import java.util.List;
import lombok.Data;

@Data
public class Depot {

  private String name;
  private Integer capacity;
  private List<Bus> parkedBuses;

}
