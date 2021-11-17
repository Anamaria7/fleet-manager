package com.flixbus.fleetmanager.service;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.dto.mapper.DepotMapper;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.repository.DepotRepository;
import com.flixbus.fleetmanager.service.validator.DepotValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepotService {

  private final DepotRepository depotRepository;
  private final DepotMapper depotMapper;
  private final DepotValidator depotValidator;

  @Autowired
  public DepotService(DepotRepository depotRepository, DepotMapper depotMapper, DepotValidator depotValidator) {
    this.depotRepository = depotRepository;
    this.depotMapper = depotMapper;
    this.depotValidator = depotValidator;
  }

  public DepotDto getById(Integer id) {
    return depotMapper.toDto(depotRepository.getById(id));
  }

  @Transactional
  public DepotDto create(DepotDto depotDto) {
    Depot depot = depotMapper.fromDto(null, depotDto);
    return depotMapper.toDto(depotRepository.saveAndFlush(depot));
  }

  @Transactional
  public DepotDto edit(Integer id, DepotDto depotDto) {
    depotValidator.validateOnEdit(id);
    Depot depot = depotMapper.fromDto(id, depotDto);
    return depotMapper.toDto(depotRepository.saveAndFlush(depot));
  }

  @Transactional
  public void delete(Integer id) {
    depotValidator.validateOnDelete(id);
    depotRepository.deleteById(id);
  }
}
