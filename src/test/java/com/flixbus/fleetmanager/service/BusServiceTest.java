package com.flixbus.fleetmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.MockitoAnnotations.openMocks;

import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.dto.mapper.BusMapper;
import com.flixbus.fleetmanager.error.ServerToClientException;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.repository.BusRepository;
import com.flixbus.fleetmanager.service.validator.BusValidator;
import com.flixbus.fleetmanager.service.validator.DepotValidator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BusServiceTest {

  @Mock
  private BusRepository busRepository;
  @Mock
  private BusMapper busMapper;
  @Mock
  private BusValidator busValidator;
  @Mock
  private DepotValidator depotValidator;

  @InjectMocks
  private BusService busService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  public void shouldGetBusById() {
    //given
    Integer id = 1;
    Bus bus = new Bus();
    Optional<Bus> optional = Optional.of(bus);
    BusDto busDto = new BusDto();

    given(busRepository.findById(id)).willReturn(optional);
    given(busMapper.toDto(bus)).willReturn(busDto);

    //when
    BusDto result = busService.getDetailsById(id);

    //then
    assertEquals(result, busDto);
  }

  @Test
  public void shouldGetBusById_null() {
    //given
    Integer id = 1;

    given(busRepository.findById(id)).willReturn(Optional.empty());

    //when
    BusDto result = busService.getDetailsById(id);

    //then
    assertEquals(result, null);
  }

  @Test
  public void shouldCreateBus() throws Exception {
    //given
    BusDto busDto = new BusDto();
    busDto.setDepotId(12);
    Bus bus = new Bus();

    Depot depot = new Depot();
    depot.setParkedBuses(Collections.emptyList());
    depot.setId(12);

    given(busMapper.fromDto(busDto)).willReturn(bus);
    given(depotValidator.validateExists(busDto.getDepotId())).willReturn(depot);
    given(busRepository.saveAndFlush(bus)).willReturn(bus);
    given(busMapper.toDto(bus)).willReturn(busDto);

    //when
    BusDto result = busService.create(busDto);

    //then
    assertEquals(busDto, result);
  }

  @Test(expected = ServerToClientException.class)
  public void shouldNotAllowCreateBus_invalidBus() throws Exception {
    //given
    BusDto busDto = new BusDto();
    doThrow(ServerToClientException.class).when(busValidator).validateOnCreate(busDto);

    //when
    busService.create(busDto);

    //then
    verifyNoInteractions(busMapper);
    verifyNoInteractions(busRepository);
  }

  @Test(expected = ServerToClientException.class)
  public void shouldNotAllowCreateBus_invalidDepot() throws Exception {
    //given
    BusDto busDto = new BusDto();
    busDto.setDepotId(12);
    Bus bus = new Bus();

    given(busMapper.fromDto(busDto)).willReturn(bus);
    doThrow(ServerToClientException.class).when(depotValidator).validateExists(busDto.getDepotId());

    //when
    busService.create(busDto);
  }

  @Test(expected = ServerToClientException.class)
  public void shouldNotAllowCreateBus_depotCapacityExceeded() throws Exception {
    //given
    BusDto busDto = new BusDto();
    busDto.setDepotId(12);
    Depot depot = new Depot();
    depot.setParkedBuses(Collections.singletonList(new Bus()));

    given(depotValidator.validateExists(busDto.getDepotId())).willReturn(depot);
    given(depotValidator.validateExists(busDto.getDepotId())).willReturn(depot);
    doThrow(ServerToClientException.class).when(depotValidator).validateCapacity(depot.getParkedBuses().size(), depot.getCapacity());

    //when
    busService.create(busDto);
  }

  @Test
  public void shouldEditBus() throws Exception {
    //given
    BusDto busDto = new BusDto();
    busDto.setDepotId(12);
    Bus bus = new Bus();

    Depot depot = new Depot();
    depot.setParkedBuses(Collections.emptyList());
    depot.setCapacity(1);
    depot.setId(12);

    given(busMapper.fromDto(busDto)).willReturn(bus);
    given(depotValidator.validateExists(busDto.getDepotId())).willReturn(depot);
    doNothing().when(depotValidator).validateCapacity(anyInt(), anyInt());
    given(busRepository.saveAndFlush(bus)).willReturn(bus);
    given(busMapper.toDto(bus)).willReturn(busDto);

    //when
    BusDto result = busService.edit(busDto);

    //then
    assertEquals(busDto, result);
  }

  @Test(expected = ServerToClientException.class)
  public void shouldNotAllowEditBus_invalidBus() throws Exception {
    //given
    Integer id = 1;
    BusDto busDto = new BusDto();
    doThrow(ServerToClientException.class).when(busValidator).validateOnEdit(busDto);

    //when
    busService.edit(busDto);

    //then
    verifyNoInteractions(busMapper);
    verifyNoInteractions(busRepository);
  }

  @Test(expected = ServerToClientException.class)
  public void shouldNotAllowEditBus_invalidDepot() throws Exception {
    //given
    BusDto busDto = new BusDto();
    busDto.setDepotId(12);
    Bus bus = new Bus();

    given(busMapper.fromDto(busDto)).willReturn(bus);
    doThrow(ServerToClientException.class).when(depotValidator).validateExists(busDto.getDepotId());

    //when
    busService.edit(busDto);
  }

  @Test(expected = ServerToClientException.class)
  public void shouldNotAllowEditBus_depotCapacityExceeded() throws Exception {
    //given
    BusDto busDto = new BusDto();
    busDto.setDepotId(12);
    Depot depot = new Depot();
    depot.setParkedBuses(Collections.singletonList(new Bus()));

    given(depotValidator.validateExists(busDto.getDepotId())).willReturn(depot);
    given(depotValidator.validateExists(busDto.getDepotId())).willReturn(depot);
    doThrow(ServerToClientException.class).when(depotValidator).validateCapacity(depot.getParkedBuses().size(), depot.getCapacity());

    //when
    busService.edit(busDto);
  }

  @Test
  public void shouldDeleteBus() throws Exception {
    //given
    Integer id = 1;

    //when
    busService.delete(id);

    //then
    verify(busRepository, times(1)).deleteById(id);
  }
}
