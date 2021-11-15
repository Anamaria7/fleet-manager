package com.flixbus.fleetmanager.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flixbus.fleetmanager.model.BusColor;
import com.flixbus.fleetmanager.model.BusType;
import com.flixbus.fleetmanager.repository.criteria.NumberTerm;
import com.flixbus.fleetmanager.repository.criteria.StringTerm;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusSearchRequest {

  @JsonProperty("plateNumber")
  private StringTerm plateNumber;

  @JsonProperty("capacity")
  private NumberTerm capacity;

  private String depotName;
  private BusType type;
  private BusColor color;

}
