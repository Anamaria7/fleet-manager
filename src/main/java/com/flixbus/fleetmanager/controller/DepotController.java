package com.flixbus.fleetmanager.controller;

import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.service.DepotService;
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
@RequestMapping("/api/depot")
public class DepotController {

  private final DepotService depotService;

  @Autowired
  public DepotController(DepotService depotService) {
    this.depotService = depotService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<DepotDto> getById(@PathVariable Integer id) {
    DepotDto depot = depotService.getById(id);
    if (depot != null) {
      return new ResponseEntity<>(depot, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping
  public ResponseEntity<DepotDto> createDepot(@RequestBody DepotDto newDepot) {
    DepotDto depot = depotService.create(newDepot);
    return new ResponseEntity<>(depot, HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<DepotDto> editDepot(@RequestBody DepotDto depotDto) {
    DepotDto depot = depotService.edit(depotDto);
    return new ResponseEntity<>(depot, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDepot(@PathVariable Integer id) {
    depotService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
