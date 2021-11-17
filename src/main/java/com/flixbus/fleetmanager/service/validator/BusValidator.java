package com.flixbus.fleetmanager.service.validator;

import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.error.ServerToClientException;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.repository.BusRepository;
import com.flixbus.fleetmanager.service.TranslationService;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class BusValidator {

  private final static Integer MIN_CAPACITY = 0;
  private final static Integer MAX_CAPACITY = 70;

  private final BusRepository busRepository;
  private final TranslationService translationService;

  public BusValidator(BusRepository busRepository, TranslationService translationService) {
    this.busRepository = busRepository;
    this.translationService = translationService;
  }

  public void validateOnCreate(Integer id, BusDto bus) {
    validatePlateNumber(id, bus);
    validateCapacity(bus.getCapacity());
  }

  public void validateOnEdit(Integer id, BusDto bus) {
    validateExists(id);
    validatePlateNumber(id, bus);
    validateCapacity(bus.getCapacity());
  }

  public void validateExists(Integer id) {
    Optional<Bus> bus = busRepository.findById(id);
    if (bus.isEmpty()) {
      throw new ServerToClientException(translationService.get("bus.not.exists", id));
    }
  }

  private void validatePlateNumber(Integer id, BusDto bus) {
    Bus existingBus = busRepository.findFirstByPlateNumberEquals(bus.getPlateNumber());
    if (existingBus != null && !existingBus.getId().equals(id)) {
      throw new ServerToClientException(translationService.get("bus.plateNumber.exists", bus.getPlateNumber()));
    }
  }

  private void validateCapacity(Integer capacity) {
    if ((capacity == null) || (capacity < MIN_CAPACITY) || (capacity > MAX_CAPACITY)) {
      throw new ServerToClientException(translationService.get("bus.capacity.invalid", capacity));
    }
  }
}
