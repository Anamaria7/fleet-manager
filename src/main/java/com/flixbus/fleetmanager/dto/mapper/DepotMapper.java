package com.flixbus.fleetmanager.dto.mapper;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.repository.BusRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepotMapper {

  private final BusRepository busRepository;

  @Autowired
  public DepotMapper(BusRepository busRepository) {
    this.busRepository = busRepository;
  }

  public DepotDto toDto(Depot depot) {
    DepotDto depotDto = new DepotDto();
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

  private List<Bus> loadDepotBuses(DepotDto depotDto) {
    List<Bus> buses = new ArrayList<>();
    if (depotDto.getParkedBusIds() != null) {
      for (Integer busId : depotDto.getParkedBusIds()) {
        buses.add(busRepository.getById(busId));
      }
    }
    return buses;
  }

  public Depot fromDto(DepotDto depotDto) {
    Depot depot = new Depot();
    depot.setCapacity(depotDto.getCapacity());
    depot.setName(depotDto.getName());

    if (depotDto.getParkedBusIds() != null && !depotDto.getParkedBusIds().isEmpty()) {
      depot.setParkedBuses(loadDepotBuses(depotDto));
    }

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
      depots.add(fromDto(depotDto));
    }
    return depots;
  }

}
