package com.flixbus.fleetmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "PLATE_NUMBER", length = 11, nullable = false, unique = true)
  private String plateNumber;

  @Enumerated(EnumType.STRING)
  private BusType type;

  @Enumerated(EnumType.STRING)
  private BusColor color;

  private Integer capacity;

//  @Column(name = "DEPOT_ID", insertable = false, updatable = false)
//  private Integer depotId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEPOT_ID", referencedColumnName = "ID") //, nullable = false)
  private Depot depot;

}
