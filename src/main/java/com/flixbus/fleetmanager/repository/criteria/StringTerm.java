package com.flixbus.fleetmanager.repository.criteria;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringTerm {

  @JsonProperty("value")
  private String value;

  @JsonProperty("filter")
  private StringFilterType filterType;

}
