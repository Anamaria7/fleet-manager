package com.flixbus.fleetmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.MockitoAnnotations.openMocks;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.dto.mapper.DepotMapper;
import com.flixbus.fleetmanager.error.ServerToClientException;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.repository.DepotRepository;
import com.flixbus.fleetmanager.service.validator.DepotValidator;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DepotServiceTest {

  @Mock
  private DepotRepository depotRepository;
  @Mock
  private DepotMapper depotMapper;
  @Mock
  private DepotValidator depotValidator;

  @InjectMocks
  private DepotService depotService;

  @BeforeEach
  public void setup() {
    openMocks(this);
  }

  @Test
  public void shouldGetDepotById() throws Exception {
    //given
    Integer id = 1;
    DepotDto depotDto = new DepotDto();
    Depot depot = new Depot();

    given(depotRepository.getById(id)).willReturn(depot);
    given(depotMapper.toDto(depot)).willReturn(depotDto);

    //when
    DepotDto result = depotService.getById(id);

    //then
    assertEquals(result, depotDto);
  }

  @Test
  public void shouldCreateDepot() throws Exception {
    //given
    DepotDto depotDto = new DepotDto();
    Depot depot = new Depot();

    given(depotMapper.fromDto(null, depotDto)).willReturn(depot);
    given(depotRepository.saveAndFlush(depot)).willReturn(depot);
    given(depotMapper.toDto(depot)).willReturn(depotDto);

    //when
    DepotDto result = depotService.create(depotDto);

    //then
    assertEquals(result, depotDto);
  }

  @Test
  public void shouldEditDepot() throws Exception {
    //given
    Integer id = 1;
    DepotDto depotDto = new DepotDto();
    Depot depot = new Depot();

    doNothing().when(depotValidator).validateOnEdit(id);
    given(depotMapper.fromDto(id, depotDto)).willReturn(depot);
    given(depotRepository.saveAndFlush(depot)).willReturn(depot);
    given(depotMapper.toDto(depot)).willReturn(depotDto);

    //when
    DepotDto result = depotService.edit(id, depotDto);

    //then
    assertEquals(result, depotDto);
  }

  @Test(expected = ServerToClientException.class)
  public void shouldNotAllowEditDepot_invalidDepot() throws Exception {
    //given
    Integer id = 1;
    doThrow(ServerToClientException.class).when(depotValidator).validateOnEdit(id);

    //when
    depotService.edit(id, new DepotDto());

    //then
    verifyNoInteractions(depotMapper);
    verifyNoInteractions(depotRepository);
  }

  @Test
  public void shouldDeleteDepot() throws Exception {
    //given
    Integer id = 1;

    doNothing().when(depotRepository).deleteById(id);

    //when
    depotService.delete(id);

    //then
    verify(depotRepository, times(1)).deleteById(id);
  }
}
