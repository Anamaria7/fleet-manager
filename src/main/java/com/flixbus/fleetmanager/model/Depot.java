package com.flixbus.fleetmanager.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "depot")
public class Depot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "DEPOT_NAME", nullable = false)
  private String name;

  private Integer capacity;

  @OneToMany(mappedBy = "depot", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Bus> parkedBuses;

  public void setParkedBuses(List<Bus> parkedBuses) {
    for (Bus bus : parkedBuses) {
      bus.setDepot(this);
    }
    this.parkedBuses = parkedBuses;
  }
}
