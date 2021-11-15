package com.flixbus.fleetmanager.repository.criteria;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NumberTerm {

  @JsonProperty("value")
  private Integer value;

  @JsonProperty("filter")
  private NumberFilterType filterType;

}
