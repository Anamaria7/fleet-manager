package com.flixbus.fleetmanager.service.validator;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.error.IllegalOperationException;
import com.flixbus.fleetmanager.error.ServerToClientException;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.repository.BusRepository;
import com.flixbus.fleetmanager.repository.DepotRepository;
import com.flixbus.fleetmanager.service.TranslationService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepotValidator {

  private final DepotRepository depotRepository;
  private final BusRepository busRepository;
  private final TranslationService translationService;

  @Autowired
  public DepotValidator(DepotRepository depotRepository, BusRepository busRepository, TranslationService translationService) {
    this.depotRepository = depotRepository;
    this.busRepository = busRepository;
    this.translationService = translationService;
  }

  public void validateOnEdit(DepotDto depotDto) {
    validateExists(depotDto.getId());
    validateCapacity(depotDto.getParkedBusIds().size(), depotDto.getCapacity());
  }

  public void validateOnDelete(Integer id) {
    List<Bus> buses = busRepository.findByDepotId(id);
    if (buses != null && !buses.isEmpty()) {
      throw new IllegalOperationException(translationService.get("depot.delete.not.allowed", id));
    }
  }

  public Depot validateExists(Integer id) {
    Optional<Depot> depot = depotRepository.findById(id);
    if (depot.isEmpty()) {
      throw new ServerToClientException(translationService.get("depot.not.exists", id));
    }
    return depot.get();
  }

  public void validateCapacity(Integer size, Integer capacity) {
    if (size - capacity >= 0) {
      throw new ServerToClientException(translationService.get("depot.capacity.exceeded"));
    }
  }
}
