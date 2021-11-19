package com.flixbus.fleetmanager.dto.mapper;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.service.validator.BusValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper
@Component
public abstract class DepotMapper {

  @Autowired
  private BusValidator busValidator;

  @Mapping(source = "parkedBuses", target = "parkedBusIds", qualifiedByName = "parkedBusIds")
//  @Mapping(expression = "java(parkedBusIds(depot.getParkedBuses()))", target = "parkedBusIds")
  public abstract DepotDto toDto(Depot depot);
  @Mapping(source = "parkedBusIds", target = "parkedBuses", qualifiedByName = "parkedBuses")
//  @Mapping(expression = "java(parkedBuses(depotDto.getParkedBusIds()))", target = "parkedBuses")
  public abstract Depot fromDto(DepotDto depotDto);
  public abstract List<DepotDto> toDtoList(List<Depot> depot);
  public abstract List<Depot> fromDtoList(List<DepotDto> depotDto);

  @Named("parkedBusIds")
  public List<Integer> parkedBusIds(List<Bus> buses) {
    if (buses != null) {
      return buses.stream().map(Bus::getId).collect(Collectors.toList());
    }
    return null;
  }

  @Named("parkedBuses")
  public List<Bus> parkedBuses(List<Integer> parkedBusIds) {
    if (parkedBusIds != null) {
      return loadDepotBuses(parkedBusIds);
    }
    return null;
  }

  private List<Bus> loadDepotBuses(List<Integer> parkedBusIds) {
    List<Bus> buses = new ArrayList<>();
    if (parkedBusIds != null) {
      buses.addAll(parkedBusIds.stream().map(busValidator::validateExists).collect(Collectors.toList()));
    }
    return buses;
  }
}
