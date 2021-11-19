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
  @Column(name = "TYPE", nullable = false)
  private BusType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "COLOR", nullable = false)
  private BusColor color;

  @Column(name = "CAPACITY", nullable = false)
  private Integer capacity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEPOT_ID", referencedColumnName = "ID")
  private Depot depot;

}
