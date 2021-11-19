package com.flixbus.fleetmanager.repository;

import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.BusColor;
import com.flixbus.fleetmanager.model.BusType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusRepository extends JpaRepository<Bus, Integer>, BusRepositoryCustom {

  Bus findFirstByPlateNumberContains(String plateNumber);
  Bus findFirstByPlateNumberEquals(String plateNumber);
  List<Bus> findByPlateNumberContains(String plateNumber);
  List<Bus> findByType(BusType type);
  List<Bus> findByColor(BusColor color);
  List<Bus> findByCapacityGreaterThanEqualOrderByCapacityDesc(Integer capacity);
  List<Bus> findByCapacityLessThanEqualOrderByCapacityDesc(Integer capacity);

  @Query("SELECT b FROM Bus b WHERE b.depot.id = ?1")
  List<Bus> findByDepotId(Integer depotId);
}
