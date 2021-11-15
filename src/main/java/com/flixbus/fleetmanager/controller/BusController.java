package com.flixbus.fleetmanager.controller;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.BusColor;
import com.flixbus.fleetmanager.model.BusType;
import com.flixbus.fleetmanager.service.BusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public Bus getBusByPlateNumber(@RequestParam(value = "plateNumber") String plateNumber) {
    return busService.getDetailsByPlateNumber(plateNumber);
  }

  @PostMapping
  public Bus createBus(@RequestBody Bus newBus) {
    return busService.create(newBus);
  }

  @PostMapping("/{id}")
  public Bus editBus(@PathVariable Integer id, @RequestBody Bus bus) {
    return busService.edit(id, bus);
  }

  @DeleteMapping("/{id}")
  public void deleteBus(@PathVariable Integer id) {
    busService.delete(id);
  }

  @PostMapping("/search")
  public List<BusDto> search(@RequestBody BusSearchRequest busSearchRequest) {
    return busService.search(busSearchRequest);
  }

  @PostMapping("/filter")
  public List<Bus> filter(@RequestBody BusSearchRequest busSearchRequest) {
    return busService.filter(busSearchRequest);
  }
}

//TODO advice
