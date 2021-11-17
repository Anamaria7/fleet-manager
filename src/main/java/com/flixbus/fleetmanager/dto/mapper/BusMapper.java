package com.flixbus.fleetmanager.dto.mapper;

import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.model.Bus;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BusMapper {

  public BusDto toDto(Bus bus) {
    BusDto busDto = new BusDto();
    busDto.setId(bus.getId());
    busDto.setPlateNumber(bus.getPlateNumber());
    busDto.setType(bus.getType());
    busDto.setColor(bus.getColor());
    busDto.setCapacity(bus.getCapacity());
    busDto.setDepotId(bus.getDepot() != null ? bus.getDepot().getId() : null);
    return busDto;
  }

  public Bus fromDto(Integer id, BusDto busDto) {
    Bus bus = new Bus();
    bus.setId(id);
    bus.setPlateNumber(busDto.getPlateNumber());
    bus.setType(busDto.getType());
    bus.setColor(busDto.getColor());
    bus.setCapacity(busDto.getCapacity());

    return bus;
  }

  public List<BusDto> toDtoList(List<Bus> buses) {
    List<BusDto> busDtos = new ArrayList<>();
    if (buses != null) {
      busDtos.addAll(buses.stream().map(this::toDto).collect(Collectors.toList()));
    }
    return busDtos;
  }

  public List<Bus> fromDtoList(List<BusDto> busDtos) {
    List<Bus> buses = new ArrayList<>();
    if (busDtos != null) {
      buses.addAll(busDtos.stream().map(bus -> fromDto(null, bus)).collect(Collectors.toList()));
    }
    return buses;
  }
}
