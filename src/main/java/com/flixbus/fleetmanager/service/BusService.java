package com.flixbus.fleetmanager.service;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.dto.mapper.BusMapper;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.repository.BusRepository;
import com.flixbus.fleetmanager.service.validator.BusValidator;
import com.flixbus.fleetmanager.service.validator.DepotValidator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusService {

  private final BusRepository busRepository;
  private final BusMapper busMapper;
  private final BusValidator busValidator;
  private final DepotValidator depotValidator;

  @Autowired
  public BusService(BusRepository busRepository, BusMapper busMapper, BusValidator busValidator, DepotValidator depotValidator) {
    this.busRepository = busRepository;
    this.busMapper = busMapper;
    this.busValidator = busValidator;
    this.depotValidator = depotValidator;
  }

  public BusDto getDetailsById(Integer id) {
    Optional<Bus> bus = busRepository.findById(id);
    if (bus.isPresent()) {
      return busMapper.toDto(bus.get());
    }
    return null;
  }

  @Transactional
  public BusDto create(BusDto busDto) {
    busValidator.validateOnCreate(busDto);
    return saveBus(busDto);
  }

  @Transactional
  public BusDto edit(BusDto busDto) {
    busValidator.validateOnEdit(busDto);
    return saveBus(busDto);
  }

  private BusDto saveBus(BusDto busDto) {
    Bus bus = busMapper.fromDto(busDto);
    if (busDto.getDepotId() != null) {
      Depot depot = depotValidator.validateExists(busDto.getDepotId());
      depotValidator.validateCapacity(depot.getParkedBuses().size(), depot.getCapacity());
      bus.setDepot(depot);
    }
    return busMapper.toDto(busRepository.saveAndFlush(bus));
  }

  public void delete(Integer id) {
    busRepository.deleteById(id);
  }

  public List<BusDto> search(BusSearchRequest busSearchRequest) {
    List<Bus> buses = busRepository.filterBuses(busSearchRequest);
    return busMapper.toDtoList(buses);
  }
}
