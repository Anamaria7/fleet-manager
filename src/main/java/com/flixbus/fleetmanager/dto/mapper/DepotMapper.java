package com.flixbus.fleetmanager.dto.mapper;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.service.validator.BusValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepotMapper {

  private final BusValidator busValidator;

  @Autowired
  public DepotMapper(BusValidator busValidator) {
    this.busValidator = busValidator;
  }

  public DepotDto toDto(Depot depot) {
    DepotDto depotDto = new DepotDto();
    depotDto.setId(depot.getId());
    depotDto.setCapacity(depot.getCapacity());
    depotDto.setName(depot.getName());

    if (depot.getParkedBuses() != null) {
      depotDto.setParkedBusIds(depot.getParkedBuses().stream().map(Bus::getId).collect(Collectors.toList()));
    }

    return depotDto;
  }

  private List<Bus> loadDepotBuses(DepotDto depotDto) {
    List<Bus> buses = new ArrayList<>();
    if (depotDto.getParkedBusIds() != null) {
      buses.addAll(depotDto.getParkedBusIds().stream().map(busValidator::validateExists).collect(Collectors.toList()));
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
    if (depots != null) {
      list.addAll(depots.stream().map(this::toDto).collect(Collectors.toList()));
    }
    return list;
  }

  public List<Depot> fromDtoList(List<DepotDto> depotDtos) {
    List<Depot> depots = new ArrayList<>();
    if (depotDtos != null) {
      depots.addAll(depotDtos.stream().map(depot -> fromDto(depot.getId(), depot)).collect(Collectors.toList()));
    }
    return depots;
  }

}
