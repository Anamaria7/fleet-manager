package com.flixbus.fleetmanager.service;

import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.repository.BusRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {

  private final BusRepository busRepository;

  @Autowired
  public BusService(BusRepository busRepository) {
    this.busRepository = busRepository;
  }

  public Bus getBusDetailsByPlateNumber(String plateNumber) {
    return busRepository.findFirstByPlateNumberContains(plateNumber);
  }

}
