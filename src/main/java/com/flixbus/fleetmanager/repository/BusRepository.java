package com.flixbus.fleetmanager.repository;

import com.flixbus.fleetmanager.model.Bus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Integer> {

  Bus findFirstByPlateNumberContains(String plateNumber);

}
