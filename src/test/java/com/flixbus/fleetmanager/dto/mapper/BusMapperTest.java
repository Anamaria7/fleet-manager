package com.flixbus.fleetmanager.dto.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.BusColor;
import com.flixbus.fleetmanager.model.BusType;
import com.flixbus.fleetmanager.model.Depot;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BusMapperTest {

  private BusMapper busMapper = new BusMapperImpl();

  @Test
  public void shouldMapToDto() {
    Bus bus = getBus();

    BusDto dto = busMapper.toDto(bus);

    assertEquals(dto.getId(), bus.getId());
    assertEquals(dto.getPlateNumber(), bus.getPlateNumber());
    assertEquals(dto.getType(), bus.getType());
    assertEquals(dto.getColor(), bus.getColor());
    assertEquals(dto.getCapacity(), bus.getCapacity());
    assertEquals(dto.getDepotId(), bus.getDepot().getId());
  }

  private Bus getBus() {
    Depot depot = new Depot();
    depot.setId(12);
    Bus bus = new Bus();
    bus.setId(1);
    bus.setPlateNumber("PLATENO");
    bus.setType(BusType.DOUBLE_DECKER);
    bus.setColor(BusColor.GREEN);
    bus.setCapacity(10);
    bus.setDepot(depot);
    return bus;
  }

  @Test
  public void shouldMapFromDto() {
    BusDto dto = getBusDto();

    Bus bus = busMapper.fromDto(dto);

    assertEquals(dto.getId(), bus.getId());
    assertEquals(dto.getPlateNumber(), bus.getPlateNumber());
    assertEquals(dto.getType(), bus.getType());
    assertEquals(dto.getColor(), bus.getColor());
    assertEquals(dto.getCapacity(), bus.getCapacity());
  }

  private BusDto getBusDto() {
    BusDto dto = new BusDto();
    dto.setId(1);
    dto.setPlateNumber("PLATENO");
    dto.setType(BusType.DOUBLE_DECKER);
    dto.setColor(BusColor.GREEN);
    dto.setCapacity(12);
    dto.setDepotId(111);
    return dto;
  }

  @Test
  public void shouldMapToDtoList() {
    Bus bus = getBus();
    List<BusDto> busDtos = busMapper.toDtoList(Collections.singletonList(bus));

    assertEquals(busDtos.size(), 1);
    assertEquals(busDtos.get(0).getId(), bus.getId());
  }

  @Test
  public void shouldMapFromDtoList() {
    BusDto busDto = getBusDto();
    List<Bus> buses = busMapper.fromDtoList(Collections.singletonList(busDto));

    assertEquals(buses.size(), 1);
    assertEquals(buses.get(0).getId(), busDto.getId());
  }

}
