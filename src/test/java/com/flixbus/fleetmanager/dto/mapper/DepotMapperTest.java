package com.flixbus.fleetmanager.dto.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.service.validator.BusValidator;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DepotMapperTest {

  @Mock
  private BusValidator busValidator;

  @InjectMocks
  private DepotMapperImpl depotMapper;

  @BeforeEach
  public void setup() {
    openMocks(this);
  }

  @Test
  public void shouldMapToDto() {
    Bus bus = getBus();
    Depot depot = getDepot(bus);

    DepotDto depotDto = depotMapper.toDto(depot);

    assertEquals(depotDto.getId(), depot.getId());
    assertEquals(depotDto.getName(), depot.getName());
    assertEquals(depotDto.getCapacity(), depot.getCapacity());
    assertEquals(depotDto.getParkedBusIds().size(), 1);
    assertEquals(depotDto.getParkedBusIds().get(0), bus.getId());
  }

  private Depot getDepot(Bus bus) {
    Depot depot = new Depot();
    depot.setId(1);
    depot.setName("Depotname");
    depot.setCapacity(12);
    depot.setParkedBuses(Collections.singletonList(bus));
    return depot;
  }

  private Bus getBus() {
    Bus bus = new Bus();
    bus.setId(123);
    return bus;
  }

  @Test
  public void shouldMapFromDto() {
    Bus bus = getBus();
    DepotDto depotDto = getDepotDto();

    given(busValidator.validateExists(123)).willReturn(bus);

    Depot depot = depotMapper.fromDto(depotDto);

    assertEquals(depotDto.getId(), depot.getId());
    assertEquals(depotDto.getName(), depot.getName());
    assertEquals(depotDto.getCapacity(), depot.getCapacity());
    assertEquals(depotDto.getParkedBusIds().size(), 1);
    assertEquals(depotDto.getParkedBusIds().get(0), bus.getId());
  }

  private DepotDto getDepotDto() {
    DepotDto depotDto = new DepotDto();
    depotDto.setId(1);
    depotDto.setName("Depotname");
    depotDto.setCapacity(12);
    depotDto.setParkedBusIds(Collections.singletonList(123));
    return depotDto;
  }

  @Test
  public void shouldMapToDtoList() {
    Bus bus = getBus();
    Depot depot = getDepot(bus);
    List<Depot> depots = Collections.singletonList(depot);

    List<DepotDto> depotDtos = depotMapper.toDtoList(depots);

    assertEquals(depotDtos.size(), 1);
    assertEquals(depotDtos.get(0).getId(), depot.getId());
    assertEquals(depotDtos.get(0).getParkedBusIds().size(), 1);
    assertEquals(depotDtos.get(0).getParkedBusIds().get(0), bus.getId());
  }

  @Test
  public void shouldMapFromDtoList() {
    DepotDto depotDto = getDepotDto();
    Bus bus = new Bus();
    bus.setId(123);
    List<DepotDto> depotDtos = Collections.singletonList(depotDto);

    given(busValidator.validateExists(bus.getId())).willReturn(bus);

    List<Depot> depots = depotMapper.fromDtoList(depotDtos);

    assertEquals(depots.size(), 1);
    assertEquals(depots.get(0).getId(), depotDto.getId());
    assertEquals(depots.get(0).getParkedBuses().size(), 1);
    assertEquals(depots.get(0).getParkedBuses().get(0), bus);
  }
}
