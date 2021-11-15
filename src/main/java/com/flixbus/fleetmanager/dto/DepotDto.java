package com.flixbus.fleetmanager.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepotDto {

  private String name;
  private Integer capacity;
  private List<Integer> parkedBusIds;

}
