package com.flixbus.fleetmanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.BusColor;
import com.flixbus.fleetmanager.model.BusType;
import com.flixbus.fleetmanager.model.Depot;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class BusRepositoryTest {

  @Resource
  private BusRepository busRepository;

  @Test
  public void test_findFirstByPlateNumberEquals() {
    Bus result = busRepository.findFirstByPlateNumberEquals("BUS-SBZ-001");
    assertEquals(result.getId(), 1);
  }

  @Test
  public void test_findFirstByPlateNumberContains() {
    Bus result = busRepository.findFirstByPlateNumberContains("US");
    assertEquals(result.getPlateNumber(), "BUS-SBZ-001");
  }

  @Test
  public void test_findByPlateNumberContains() {
    List<Bus> result = busRepository.findByPlateNumberContains("BUS");
    assertEquals(result.size(), 6);
  }

  @Test
  public void test_findByType() {
    List<Bus> result = busRepository.findByType(BusType.DOUBLE_DECKER);
    assertEquals(result.size(), 3);
  }

  @Test
  public void test_findByColor() {
    List<Bus> result = busRepository.findByColor(BusColor.GREEN);
    assertEquals(result.size(), 4);
  }

  @Test
  public void test_findByCapacityGreaterThanEqualOrderByCapacityDesc() {
    List<Bus> result = busRepository.findByCapacityGreaterThanEqualOrderByCapacityDesc(30);
    assertEquals(result.size(), 5);
  }

  @Test
  public void test_findByCapacityLessThanEqualOrderByCapacityDesc() {
    List<Bus> result = busRepository.findByCapacityLessThanEqualOrderByCapacityDesc(30);
    assertEquals(result.size(), 2);
  }

  @Test
  public void test_findByDepotId() {
    List<Bus> result = busRepository.findByDepotId(4);
    assertEquals(result.size(), 3);
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void test_depotId_FK_violation() {
    Bus bus = new Bus();
    bus.setPlateNumber("PLATENO");
    bus.setCapacity(1);

    Depot depot = new Depot();
    depot.setId(10);
    depot.setName("INEXISTENT");
    depot.setCapacity(100);

    bus.setDepot(depot);

    busRepository.save(bus);
  }
}
//TODO mapstruct
//for loops
//filters
//entity annotations
