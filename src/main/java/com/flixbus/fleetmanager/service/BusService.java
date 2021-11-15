package com.flixbus.fleetmanager.service;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.dto.mapper.BusMapper;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.repository.BusRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {

  private final BusRepository busRepository;
  private final SpecificationProvider specificationProvider;
  private final BusMapper busMapper;

  @Autowired
  public BusService(BusRepository busRepository, SpecificationProvider specificationProvider, BusMapper busMapper) {
    this.busRepository = busRepository;
    this.specificationProvider = specificationProvider;
    this.busMapper = busMapper;
  }

  public Bus getById(Integer id) {
    return busRepository.getById(id);
  }

  public Bus getDetailsByPlateNumber(String plateNumber) {
    return busRepository.findFirstByPlateNumberContains(plateNumber);
  }

  public Bus create(Bus newBus) {
    //validations
    return busRepository.saveAndFlush(newBus);
  }

  public Bus edit(Integer id, Bus bus) {
    Bus currentBus = getById(id);
    currentBus.setPlateNumber(bus.getPlateNumber());
    currentBus.setType(bus.getType());
    currentBus.setColor(bus.getColor());
    currentBus.setCapacity(bus.getCapacity());
    currentBus.setDepotId(bus.getDepotId());
    return busRepository.saveAndFlush(currentBus);
  }

  public void delete(Integer id) {
    busRepository.deleteById(id);
  }

  public List<BusDto> search(BusSearchRequest busSearchRequest) {
//    Specification<Bus> specification = specificationProvider.createBusSpecification(busSearchRequest);
//    return busRepository.findAll(specification);

//    BooleanExpression combinedFilter = combinedFilterExpression(busSearchRequest);
//    return (List<Bus>) busRepository.findAll(combinedFilter);
//    return busRepository.searchByCombinedFilter(busSearchRequest.)

    List<Bus> buses = busRepository.filterBuses(busSearchRequest);
    return busMapper.toDtoList(buses);
  }

  public List<Bus> filter(BusSearchRequest busSearchRequest) {
//    return busRepository.findByPlateNumberContains(busSearchRequest.getPlateNumber());
//    return busRepository.findByType(BusType.DOUBLE_DECKER);
//    return busRepository.findByColor(BusColor.GREEN);
    return busRepository.findByCapacityGreaterThanEqualOrderByCapacityDesc(40);
  }
}
