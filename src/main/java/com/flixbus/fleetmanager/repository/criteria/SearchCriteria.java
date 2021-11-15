package com.flixbus.fleetmanager.repository.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {

  private String key;
  private String operation;
  private Object value;

  public boolean isOrOperation() {
    return "OR".equals(operation);
  }
}
