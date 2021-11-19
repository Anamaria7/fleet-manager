package com.flixbus.fleetmanager.controller;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.service.BusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bus")
public class BusController {

  private final BusService busService;

  @Autowired
  public BusController(BusService busService) {
    this.busService = busService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<BusDto> getBusById(@PathVariable Integer id) {
    BusDto bus = busService.getDetailsById(id);
    if (bus != null) {
      return new ResponseEntity<>(bus, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping
  public ResponseEntity<BusDto> createBus(@RequestBody BusDto newBus) {
    BusDto bus = busService.create(newBus);
    return new ResponseEntity<>(bus, HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<BusDto> updateBus(@RequestBody BusDto bus) {
    BusDto busDto = busService.edit(bus);
    return new ResponseEntity<>(busDto, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBus(@PathVariable Integer id) {
    busService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/search")
  public ResponseEntity<List<BusDto>> search(@RequestBody BusSearchRequest busSearchRequest) {
    List<BusDto> buses = busService.search(busSearchRequest);
    if (buses != null && !buses.isEmpty()) {
      return new ResponseEntity<>(buses, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
