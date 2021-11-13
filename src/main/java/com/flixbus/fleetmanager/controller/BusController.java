package com.flixbus.fleetmanager.controller;

import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.service.BusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bus")
public class BusController {

  private final BusService busService;

  @Autowired
  public BusController(BusService busService) {
    this.busService = busService;
  }

  @GetMapping()
  public Bus getBusByPlateNumber(@RequestParam(value = "plateNumber", defaultValue = "") String plateNumber) {
    return busService.getBusDetailsByPlateNumber(plateNumber);
  }
}
