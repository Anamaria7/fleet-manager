package com.flixbus.fleetmanager.service;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.repository.criteria.BusSpecificationsBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationProvider {

  public Specification<Bus> createBusSpecification(BusSearchRequest busSearchRequest) {
    BusSpecificationsBuilder builder = new BusSpecificationsBuilder();
    builder.with("plateNumber", ":", busSearchRequest.getPlateNumber());
    builder.with("type", ":", busSearchRequest.getType());
    builder.with("color", ":", busSearchRequest.getColor());
    builder.with("capacity", ":", busSearchRequest.getCapacity());
    builder.with("depotName", ":", busSearchRequest.getDepotName());
    Specification<Bus> specification = builder.build();
    return specification;
  }

}
