package com.flixbus.fleetmanager.dto.mapper;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.repository.BusRepository;
import com.flixbus.fleetmanager.service.validator.BusValidator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepotMapper {

  private final BusRepository busRepository;
  private final BusValidator busValidator;

  @Autowired
  public DepotMapper(BusRepository busRepository, BusValidator busValidator) {
    this.busRepository = busRepository;
    this.busValidator = busValidator;
  }

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

  private List<Bus> loadDepotBuses(DepotDto depotDto) {
    List<Bus> buses = new ArrayList<>();
    if (depotDto.getParkedBusIds() != null) {
      for (Integer busId : depotDto.getParkedBusIds()) {
        buses.add(busValidator.validateExists(busId));
      }
    }
    return buses;
  }

  public Depot fromDto(Integer id, DepotDto depotDto) {
    Depot depot = new Depot();
    depot.setId(id);
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
      depots.add(fromDto(depotDto.getId(), depotDto));
    }
    return depots;
  }

}
