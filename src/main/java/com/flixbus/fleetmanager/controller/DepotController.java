package com.flixbus.fleetmanager.controller;

import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.service.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  public DepotDto getById(@PathVariable Integer id) {
    return depotService.getById(id);
  }

  @PostMapping
  public DepotDto createDepot(@RequestBody DepotDto newDepot) {
    return depotService.create(newDepot);
  }

  @PostMapping("/{id}")
  public DepotDto editDepot(@PathVariable Integer id, @RequestBody DepotDto depotDto) {
    return depotService.edit(id, depotDto);
  }

  @DeleteMapping("/{id}")
  public void deleteDepot(@PathVariable Integer id) {
    depotService.delete(id);
  }

}
