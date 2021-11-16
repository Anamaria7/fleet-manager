package com.flixbus.fleetmanager.service;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.dto.mapper.BusMapper;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.repository.BusRepository;
import com.flixbus.fleetmanager.repository.DepotRepository;
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
  private final DepotRepository depotRepository;
  private final BusMapper busMapper;
  private final BusValidator busValidator;
  private final DepotValidator depotValidator;

  @Autowired
  public BusService(BusRepository busRepository, DepotRepository depotRepository, BusMapper busMapper,
      BusValidator busValidator, DepotValidator depotValidator) {
    this.busRepository = busRepository;
    this.depotRepository = depotRepository;
    this.busMapper = busMapper;
    this.busValidator = busValidator;
    this.depotValidator = depotValidator;
  }

  public BusDto getDetailsByPlateNumber(String plateNumber) {
    Bus bus = busRepository.findFirstByPlateNumberEquals(plateNumber);
    if (bus != null) {
      return busMapper.toDto(bus);
    }
    return null;
  }

  @Transactional
  public BusDto create(BusDto busDto) {
    busValidator.validateOnCreate(null, busDto);
    Bus bus = busMapper.fromDto(null, busDto);
    if (busDto.getDepotId() != null) {
      Depot depot = depotRepository.getById(busDto.getDepotId());
      depotValidator.validateCapacityOnAddBus(depot);
      bus.setDepot(depot);
    }
    return busMapper.toDto(busRepository.saveAndFlush(bus));
  }

  @Transactional
  public BusDto edit(Integer id, BusDto busDto) {
    busValidator.validateOnEdit(id, busDto);
    Bus bus = busMapper.fromDto(id, busDto);
    if (busDto.getDepotId() != null) {
      Depot depot = depotValidator.validateExists(busDto.getDepotId());
      depotValidator.validateCapacityOnAddBus(depot);
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

  public List<BusDto> filter(BusSearchRequest busSearchRequest) {
//    return busRepository.findByPlateNumberContains(busSearchRequest.getPlateNumber());
//    return busRepository.findByType(BusType.DOUBLE_DECKER);
//    return busRepository.findByColor(BusColor.GREEN);
    return busMapper.toDtoList(busRepository.findByCapacityGreaterThanEqualOrderByCapacityDesc(40));
  }
}
