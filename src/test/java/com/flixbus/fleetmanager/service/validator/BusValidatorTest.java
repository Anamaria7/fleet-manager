package com.flixbus.fleetmanager.service.validator;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.MockitoAnnotations.openMocks;

import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.error.ServerToClientException;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.repository.BusRepository;
import com.flixbus.fleetmanager.service.TranslationService;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BusValidatorTest {

  @Mock
  private BusRepository busRepository;
  @Mock
  private TranslationService translationService;

  @InjectMocks
  private BusValidator busValidator;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  public void validateExists_true() {
    //given
    Integer id = 1;
    Bus bus = new Bus();
    Optional<Bus> optional = Optional.of(bus);

    given(busRepository.findById(id)).willReturn(optional);

    //when
    busValidator.validateExists(id);

    //then
    verifyNoInteractions(translationService);
  }

  @Test(expected = ServerToClientException.class)
  public void validateExists_false() {
    //given
    Integer id = 1;
    Optional<Bus> optional = Optional.empty();

    given(busRepository.findById(id)).willReturn(optional);

    //when
    busValidator.validateExists(id);

    //then
    verify(translationService, times(1)).get("bus.not.exists", id);
  }

  @Test
  public void validateOnCreate_true() {
    //given
    BusDto bus = new BusDto();
    bus.setPlateNumber("PLATENO");
    bus.setCapacity(50);

    given(busRepository.findFirstByPlateNumberEquals(bus.getPlateNumber())).willReturn(null);

    //when
    busValidator.validateOnCreate(null, bus);

    //then
    verifyNoInteractions(translationService);
  }

  @Test(expected = ServerToClientException.class)
  public void validateOnCreate_false_existingPlateNumber() {
    //given
    BusDto bus = new BusDto();
    bus.setId(14);
    bus.setPlateNumber("PLATENO");
    bus.setCapacity(50);

    Bus existingBus = new Bus();
    existingBus.setId(12);

    given(busRepository.findFirstByPlateNumberEquals(bus.getPlateNumber())).willReturn(existingBus);

    //when
    busValidator.validateOnCreate(null, bus);

    //then
    verify(translationService, times(1)).get("bus.plateNumber.exists", bus.getPlateNumber());
  }

  @Test(expected = ServerToClientException.class)
  public void validateOnCreate_false_invalidCapacity() {
    //given
    BusDto bus = new BusDto();
    bus.setCapacity(100);

    given(busRepository.findFirstByPlateNumberEquals(bus.getPlateNumber())).willReturn(null);

    //when
    busValidator.validateOnCreate(null, bus);

    //then
    verify(translationService, times(1)).get("bus.capacity.invalid", bus.getCapacity());
  }

  @Test
  public void validateOnEdit_true() {
    //given
    Integer id = 1;
    BusDto bus = new BusDto();
    bus.setPlateNumber("PLATENO");
    bus.setCapacity(50);
    Optional<Bus> optional = Optional.of(new Bus());

    given(busRepository.findById(id)).willReturn(optional);
    given(busRepository.findFirstByPlateNumberEquals(bus.getPlateNumber())).willReturn(null);

    //when
    busValidator.validateOnEdit(id, bus);

    //then
    verifyNoInteractions(translationService);
  }

  @Test(expected = ServerToClientException.class)
  public void validateOnEdit_false_busDoesNotExist() {
    //given
    Integer id = 1;
    BusDto bus = new BusDto();

    given(busRepository.findById(id)).willReturn(Optional.empty());

    //when
    busValidator.validateOnEdit(id, bus);

    //then
    verify(translationService, times(1)).get("bus.not.exists", id);
  }

  @Test(expected = ServerToClientException.class)
  public void validateOnEdit_false_existingPlateNumber() {
    //given
    Integer id = 1;
    BusDto bus = new BusDto();
    bus.setId(14);
    bus.setPlateNumber("PLATENO");
    bus.setCapacity(50);

    Bus existingBus = new Bus();
    existingBus.setId(12);

    given(busRepository.findFirstByPlateNumberEquals(bus.getPlateNumber())).willReturn(existingBus);

    //when
    busValidator.validateOnCreate(null, bus);

    //then
    verify(translationService, times(1)).get("bus.plateNumber.exists", bus.getPlateNumber());
  }

  @Test(expected = ServerToClientException.class)
  public void validateOnEdit_false_invalidCapacity() {
    //given
    Integer id = 1;
    BusDto bus = new BusDto();
    bus.setCapacity(100);

    given(busRepository.findById(id)).willReturn(Optional.empty());

    //when
    busValidator.validateOnEdit(id, bus);

    //then
    verify(translationService, times(1)).get("bus.capacity.invalid", bus.getCapacity());
  }
}
