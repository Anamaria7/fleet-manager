package com.flixbus.fleetmanager.service.validator;

import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.repository.BusRepository;
import org.springframework.stereotype.Component;

@Component
public class BusValidator {

  private final static Integer MIN_CAPACITY = 0;
  private final static Integer MAX_CAPACITY = 70;

  private final BusRepository busRepository;

  public BusValidator(BusRepository busRepository) {
    this.busRepository = busRepository;
  }

  public void validate(Integer id, BusDto bus) {
    validatePlateNumber(id, bus);
    validateCapacity(bus.getCapacity());
  }

  public void validateExists(Integer id) {
    if (id != null && busRepository.findById(id).isEmpty()) {
      throw new IllegalArgumentException("Bus with id " + id + " does not exist");
    }
  }

  private void validatePlateNumber(Integer id, BusDto bus) {
    Bus existingBus = busRepository.findFirstByPlateNumberEquals(bus.getPlateNumber());
    if (existingBus != null && !existingBus.getId().equals(id)) {
      throw new IllegalArgumentException("Bus with plateNumber " + bus.getPlateNumber() + " already exists");
    }
  }

  private void validateCapacity(Integer capacity) {
    if ((capacity < MIN_CAPACITY) || (capacity > MAX_CAPACITY)) {
      throw new IllegalArgumentException("Bus capacity invalid: " + "capacity");
    }
  }
}
