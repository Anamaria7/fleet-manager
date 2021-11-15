package com.flixbus.fleetmanager.repository.criteria;

import com.flixbus.fleetmanager.model.Bus;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;

public class BusSpecificationsBuilder {

  private final List<SearchCriteria> params;

  public BusSpecificationsBuilder() {
    params = new ArrayList<>();
  }

  public BusSpecificationsBuilder with(String key, String operation, Object value) {
//    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public Specification<Bus> build() {
    if (params.size() == 0) {
      return null;
    }

    List<Specification> specs = params.stream()
        .map(BusSpecification::new)
        .collect(Collectors.toList());

    Specification result = null;

    for (int i = 1; i < params.size(); i++) {
      result = params.get(i)
          .isOrOperation()
          ? Specification.where(result)
          .or(specs.get(i))
          : Specification.where(result)
              .and(specs.get(i));
    }
    return result;
  }

}
