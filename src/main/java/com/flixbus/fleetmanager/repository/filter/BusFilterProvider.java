package com.flixbus.fleetmanager.repository.filter;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.model.QBus;
import com.flixbus.fleetmanager.model.QDepot;
import com.querydsl.core.JoinExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BusFilterProvider {

  public BooleanExpression combinedFilterExpression(BusSearchRequest busSearchRequest) {
    QBus bus = QBus.bus;
    QDepot depot = QDepot.depot;
    BooleanExpression plateNumberExpression = bus.plateNumber.contains(busSearchRequest.getPlateNumber());
    BooleanExpression typeExpression = bus.type.eq(busSearchRequest.getType());
    BooleanExpression colorExpression = bus.color.eq(busSearchRequest.getColor());
    BooleanExpression capacityExpression = null;
//    Integer capacity = busSearchRequest.getCapacity().getFirst();
//    if (busSearchRequest.getCapacity() != null) {
//      switch (busSearchRequest.getCapacity().getSecond()) {
//        case EQUALS:
//          capacityExpression = bus.capacity.eq(capacity);
//          break;
//        case LT:
//          capacityExpression = bus.capacity.lt(capacity);
//          break;
//        case LTE:
//          capacityExpression = bus.capacity.lt(capacity).or(bus.capacity.eq(capacity));
//          break;
//        case GT:
//          capacityExpression = bus.capacity.gt(capacity);
//          break;
//        case GTE:
//          capacityExpression = bus.capacity.gt(capacity).or(bus.capacity.eq(capacity));
//          break;
//      }
//    }

    JPQLQuery<Bus> query = new JPAQuery<>();
    List<Bus> buses = query.from(bus).innerJoin(depot).where(depot.name.eq(busSearchRequest.getDepotName())).fetch();

//    Depot depot1 = depot.name.eq(busSearchRequest.getDepotName());
//    BooleanExpression depotNameExpression = bus.depotId.eq(depot1.getId());


    BooleanExpression combinedFilter = plateNumberExpression
        .and(typeExpression)
        .and(colorExpression)
        .and(capacityExpression);
//        .and(depotNameExpression);
    return combinedFilter;
  }

}
