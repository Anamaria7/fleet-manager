package com.flixbus.fleetmanager.controller;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.dto.BusDto;
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
  public BusDto getBusByPlateNumber(@RequestParam(value = "plateNumber") String plateNumber) {
    return busService.getDetailsByPlateNumber(plateNumber);
  }

  @PostMapping
  public BusDto createBus(@RequestBody BusDto newBus) {
    return busService.create(newBus);
  }

  @PostMapping("/{id}")
  public BusDto editBus(@PathVariable Integer id, @RequestBody BusDto bus) {
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
  public List<BusDto> filter(@RequestBody BusSearchRequest busSearchRequest) {
    return busService.filter(busSearchRequest);
  }
}

//TODO filter
//TODO unit tests
