package com.flixbus.fleetmanager.repository.criteria;

import com.flixbus.fleetmanager.controller.request.OperationType;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.BusColor;
import com.flixbus.fleetmanager.model.BusType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BusQueryBuilder {

  public static final String FIELD_PLATE_NUMBER = "plateNumber";
  public static final String FIELD_TYPE = "type";
  public static final String FIELD_COLOR = "color";
  public static final String FIELD_CAPACITY = "capacity";
  public static final String TABLE_DEPOT = "depot";
  public static final String TABLE_DEPOT_FIELD_NAME = "name";

  private final List<Predicate> predicates;
  private final CriteriaQuery<Bus> query;
  private CriteriaBuilder criteriaBuilder;
  private Root<Bus> bus;

  public BusQueryBuilder(CriteriaBuilder criteriaBuilder) {
    this.criteriaBuilder = criteriaBuilder;
    this.query = criteriaBuilder.createQuery(Bus.class);
    this.bus = query.from(Bus.class);;
    predicates = new ArrayList<>();
  }

  public BusQueryBuilder withPlateNumber(OperationType operation, String value) {
    if (value != null) { //TODO operation
      predicates.add(criteriaBuilder.like(bus.get(FIELD_PLATE_NUMBER), "%" + value + "%"));
    }
    return this;
  }

  public BusQueryBuilder withBusType(BusType value) {
    if (value != null) {
      predicates.add(criteriaBuilder.equal(bus.get(FIELD_TYPE), value));
    }
    return this;
  }

  public BusQueryBuilder withBusColor(BusColor value) {
    if (value != null) {
      predicates.add(criteriaBuilder.equal(bus.get(FIELD_COLOR), value));
    }
    return this;
  }

  public BusQueryBuilder withBusCapacity(OperationType operation, Integer value) {
    if (value != null) {
      if (operation != null) {
        switch (operation) {
          case EQUALS:
            predicates.add(criteriaBuilder.equal(bus.get(FIELD_CAPACITY), value));
            break;
          case LT:
            predicates.add(criteriaBuilder.lessThan(bus.get(FIELD_CAPACITY), value));
            break;
          case LTE:
            predicates.add(criteriaBuilder.lessThanOrEqualTo(bus.get(FIELD_CAPACITY), value));
            break;
          case GT:
            predicates.add(criteriaBuilder.greaterThan(bus.get(FIELD_CAPACITY), value));
            break;
          case GTE:
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(bus.get(FIELD_CAPACITY), value));
            break;
        }
      }
    }
    return this;
  }

  public BusQueryBuilder withDepotName(String value) {
    if (value != null) {
      predicates.add(criteriaBuilder.equal(bus.join(TABLE_DEPOT).get(TABLE_DEPOT_FIELD_NAME), value));
    }
    return this;
  }

  public CriteriaQuery<Bus> build() {
    if (predicates.size() == 0) {
      return null;
    }

//    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//    CriteriaQuery<Bus> query = criteriaBuilder.createQuery(Bus.class);
//    Root<Bus> bus = query.from(Bus.class);


    query.
        select(bus).where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
    return query;
  }

}
