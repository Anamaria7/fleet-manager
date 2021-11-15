package com.flixbus.fleetmanager.service.validator;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.repository.BusRepository;
import com.flixbus.fleetmanager.repository.DepotRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepotValidator {

  private final DepotRepository depotRepository;
  private final BusRepository busRepository;
  private final BusValidator busValidator;

  @Autowired
  public DepotValidator(DepotRepository depotRepository, BusRepository busRepository, BusValidator busValidator) {
    this.depotRepository = depotRepository;
    this.busRepository = busRepository;
    this.busValidator = busValidator;
  }

  public void validateOnCreate(DepotDto depotDto) {
    validateCapacity(depotDto);
    validateBuses(depotDto.getParkedBusIds());
  }

  public void validateOnEdit(Integer id, DepotDto depotDto) {
    validateExists(id);
    validateCapacity(depotDto);
    validateBuses(depotDto.getParkedBusIds());
  }

  public void validateExists(Integer id) {
    if (id!= null && depotRepository.findById(id).isEmpty()) {
      throw new IllegalArgumentException("Depot with id " + id + " does not exist");
    }
  }

  private void validateBuses(List<Integer> parkedBusIds) {
    if (parkedBusIds != null) {
      for (Integer busId : parkedBusIds) {
        busValidator.validateExists(busId);
      }
    }
  }

  private void validateCapacity(DepotDto depotDto) {
    int exceedBy = depotDto.getParkedBusIds().size() - depotDto.getCapacity();
    if (depotDto != null && exceedBy > 0) {
      throw new IllegalArgumentException("Bus capacity exceeded by " + exceedBy + " buses");
    }
  }

  public void validateOnDelete(Integer id) {
    List<Bus> buses = busRepository.findByDepotId(id);
    if (buses != null && !buses.isEmpty()) {
      throw new IllegalStateException("Cannot delete depot with id " + id + ". Buses connected");
    }
  }
}
