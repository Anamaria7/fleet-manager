package com.flixbus.fleetmanager.dto.mapper;

import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.model.Bus;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BusMapper { //TODO mapstruct - unit tests

  public BusDto toDto(Bus bus) {
    BusDto busDto = new BusDto();
    busDto.setId(bus.getId());
    busDto.setPlateNumber(bus.getPlateNumber());
    busDto.setType(bus.getType());
    busDto.setColor(bus.getColor());
    busDto.setCapacity(bus.getCapacity());
    busDto.setDepotId(bus.getDepotId());
    return busDto;
  }

  public List<BusDto> toDtoList(List<Bus> buses) {
    List<BusDto> busDtos = new ArrayList<>();
    for (Bus bus : buses) {
      busDtos.add(toDto(bus));
    }
    return busDtos;
  }
}
