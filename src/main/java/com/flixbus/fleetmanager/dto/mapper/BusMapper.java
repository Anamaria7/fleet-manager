package com.flixbus.fleetmanager.dto.mapper;

import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.model.Bus;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BusMapper {

  public BusDto toDto(Bus bus) {
    BusDto busDto = new BusDto();
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
    for (Bus bus : buses) {
      busDtos.add(toDto(bus));
    }
    return busDtos;
  }

  public List<Bus> fromDtoList(List<BusDto> busDtos) {
    List<Bus> buses = new ArrayList<>();
    for (BusDto bus : busDtos) {
      buses.add(fromDto(null, bus));
    }
    return buses;
  }
}
