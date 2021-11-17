package com.flixbus.fleetmanager.dto.mapper;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DepotMapper {

  public DepotDto toDto(Depot depot) {
    DepotDto depotDto = new DepotDto();
    depotDto.setId(depot.getId());
    depotDto.setCapacity(depot.getCapacity());
    depotDto.setName(depot.getName());

    if (depot.getParkedBuses()!= null && !depot.getParkedBuses().isEmpty()) {
      List<Integer> buses = new ArrayList<>();
      for (Bus bus : depot.getParkedBuses()) {
        buses.add(bus.getId());
      }
      depotDto.setParkedBusIds(buses);
    }

    return depotDto;
  }

  public Depot fromDto(Integer id, DepotDto depotDto) {
    Depot depot = new Depot();
    depot.setId(id);
    depot.setCapacity(depotDto.getCapacity());
    depot.setName(depotDto.getName());
    return depot;
  }

  public List<DepotDto> toDtoList(List<Depot> depots) {
    List<DepotDto> list = new ArrayList<>();
    for (Depot depot : depots) {
      list.add(toDto(depot));
    }
    return list;
  }

  public List<Depot> fromDtoList(List<DepotDto> depotDtos) {
    List<Depot> depots = new ArrayList<>();
    for (DepotDto depotDto : depotDtos) {
      depots.add(fromDto(depotDto.getId(), depotDto));
    }
    return depots;
  }

}
