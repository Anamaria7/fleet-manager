package com.flixbus.fleetmanager.repository;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.BusColor;
import com.flixbus.fleetmanager.model.BusType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BusRepositoryCustomImpl implements BusRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Bus> filterBuses(BusSearchRequest busSearchRequest) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Bus> query = cb.createQuery(Bus.class);
    Root<Bus> bus = query.from(Bus.class);

    //TODO create builder for predicates, extract strings to constants
    List<Predicate> predicates = new ArrayList<>();
    Path<String> plateNumberPath = bus.get("plateNumber");
    Path<BusType> busTypePath = bus.get("type");
    Path<BusColor> busColorPath = bus.get("color");
    Path<Integer> capacityPath = bus.get("capacity");

    predicates.add(cb.like(plateNumberPath, "%" + busSearchRequest.getPlateNumber() + "%"));
    predicates.add(cb.equal(busTypePath, busSearchRequest.getType()));
    predicates.add(cb.equal(busColorPath, busSearchRequest.getColor()));

    switch (busSearchRequest.getOperation()) {
      case EQUALS:
        predicates.add(cb.equal(capacityPath, busSearchRequest.getCapacity()));
        break;
      case LT:
        predicates.add(cb.lessThan(capacityPath, busSearchRequest.getCapacity()));
        break;
      case LTE:
        predicates.add(cb.lessThanOrEqualTo(capacityPath, busSearchRequest.getCapacity()));
        break;
      case GT:
        predicates.add(cb.greaterThan(capacityPath, busSearchRequest.getCapacity()));
        break;
      case GTE:
        predicates.add(cb.greaterThanOrEqualTo(capacityPath, busSearchRequest.getCapacity()));
        break;
    }

    query.select(bus).where(cb.equal(bus.join("depot").get("name"), busSearchRequest.getDepotName()))
        .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

    return entityManager.createQuery(query).getResultList();
  }
}
