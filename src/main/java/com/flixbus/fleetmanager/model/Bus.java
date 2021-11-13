package com.flixbus.fleetmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Bus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

//  @Column(name = "PLATE_NUMBER", length = 11, nullable = false, unique = true)
  private String plateNumber;

  @Enumerated(EnumType.STRING)
  private BusType type;

  @Enumerated
  private BusColor color;

  private Integer capacity;

  @Column(name = "DEPOT_ID")
  private Integer depotId;

}