package com.flixbus.fleetmanager.service.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.MockitoAnnotations.openMocks;

import com.flixbus.fleetmanager.error.IllegalOperationException;
import com.flixbus.fleetmanager.error.ServerToClientException;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.repository.BusRepository;
import com.flixbus.fleetmanager.repository.DepotRepository;
import com.flixbus.fleetmanager.service.TranslationService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DepotValidatorTest {

  @Mock
  private DepotRepository depotRepository;
  @Mock
  private BusRepository busRepository;
  @Mock
  private TranslationService translationService;

  @InjectMocks
  private DepotValidator depotValidator;

  @BeforeEach
  public void setup() {
    openMocks(this);
  }

  @Test
  public void validateExists_exists() {
    //given
    Integer id = 1;
    Optional<Depot> optional = Optional.of(new Depot());

    given(depotRepository.findById(id)).willReturn(optional);

    //when
    Depot depot = depotValidator.validateExists(id);

    //then
    assertEquals(depot, optional.get());
    verifyNoInteractions(translationService);
  }

  @Test(expected = ServerToClientException.class)
  public void validateExists_notExists() {
    //given
    Integer id = 1;
    Optional<Depot> optional = Optional.empty();

    given(depotRepository.findById(id)).willReturn(optional);

    //when
    depotValidator.validateExists(id);
  }

  @Test
  public void validateOnDelete_allowed() {
    //given
    Integer id = 1;
    given(busRepository.findByDepotId(id)).willReturn(null);

    //when
    depotValidator.validateOnDelete(id);

    //then
    verifyNoInteractions(translationService);
  }

  @Test(expected = IllegalOperationException.class)
  public void validateOnDelete_not_allowed() {
    //given
    Integer id = 1;
    List<Bus> buses = Collections.singletonList(new Bus());

    given(busRepository.findByDepotId(id)).willReturn(buses);

    //when
    depotValidator.validateOnDelete(id);
  }

  @Test
  public void validateCapacityOnAddBus_valid() {
    //given
    Depot depot = new Depot();
    depot.setCapacity(4);
    Bus bus1 = new Bus();
    Bus bus2 = new Bus();
    depot.setParkedBuses(Arrays.asList(bus1, bus2));

    //when
    depotValidator.validateCapacityOnAddBus(depot);

    //then
    verifyNoInteractions(translationService);
  }

  @Test(expected = ServerToClientException.class)
  public void validateCapacityOnAddBus_invalid() {
    //given
    Depot depot = new Depot();
    depot.setCapacity(2);
    Bus bus1 = new Bus();
    Bus bus2 = new Bus();
    depot.setParkedBuses(Arrays.asList(bus1, bus2));

    //when
    depotValidator.validateCapacityOnAddBus(depot);
  }
}
