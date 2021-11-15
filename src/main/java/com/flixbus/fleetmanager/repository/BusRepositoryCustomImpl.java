package com.flixbus.fleetmanager.repository;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.controller.request.OperationType;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.repository.criteria.BusQueryBuilder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class BusRepositoryCustomImpl implements BusRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Bus> filterBuses(BusSearchRequest busSearchRequest) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<Bus> query = new BusQueryBuilder(cb) //TODO operation
        .withPlateNumber(OperationType.CONTAINS, busSearchRequest.getPlateNumber())
        .withBusType(busSearchRequest.getType())
        .withBusColor(busSearchRequest.getColor())
        .withBusCapacity(OperationType.GTE, busSearchRequest.getCapacity())
        .withDepotName(busSearchRequest.getDepotName())
        .build();

    return entityManager.createQuery(query).getResultList();
  }
}
