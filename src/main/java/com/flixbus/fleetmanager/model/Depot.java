package com.flixbus.fleetmanager.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class Depot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "DEPOT_NAME", nullable = false)
  private String name;

  private Integer capacity;

  @Transient
  private List<Bus> parkedBuses;

}
