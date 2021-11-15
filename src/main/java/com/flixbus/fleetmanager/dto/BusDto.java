package com.flixbus.fleetmanager.dto;

import com.flixbus.fleetmanager.model.BusColor;
import com.flixbus.fleetmanager.model.BusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDto {

  private String plateNumber;
  private BusType type;
  private BusColor color;
  private Integer capacity;
  private Integer depotId;

}
