package com.flixbus.fleetmanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.BusColor;
import com.flixbus.fleetmanager.model.BusType;
import com.flixbus.fleetmanager.model.Depot;
import com.flixbus.fleetmanager.repository.criteria.StringFilterType;
import com.flixbus.fleetmanager.repository.criteria.StringTerm;
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

  @Test
  public void test_filterBuses_byPlateNumber_CONTAINS() {
    BusSearchRequest busSearchRequest = new BusSearchRequest();
    busSearchRequest.setPlateNumber(new StringTerm("BUS", StringFilterType.CONTAINS));

    List<Bus> result = busRepository.filterBuses(busSearchRequest);

    assertEquals(result.size(), 6);
  }

  @Test
  public void test_filterBuses_byPlateNumber_EQUALS() {
    BusSearchRequest busSearchRequest = new BusSearchRequest();
    busSearchRequest.setPlateNumber(new StringTerm("BUS-SBZ-001", StringFilterType.EQUALS));

    List<Bus> result = busRepository.filterBuses(busSearchRequest);

    assertEquals(result.size(), 1);
    assertEquals(result.get(0).getPlateNumber(), "BUS-SBZ-001");
  }

  @Test
  public void test_filterBuses_byPlateNumber_EQUALS_IGNORE_CASE() {
    BusSearchRequest busSearchRequest = new BusSearchRequest();
    busSearchRequest.setPlateNumber(new StringTerm("BuS-SbZ-001", StringFilterType.EQUALS_IGNORE_CASE));

    List<Bus> result = busRepository.filterBuses(busSearchRequest);

    assertEquals(result.size(), 1);
    assertEquals(result.get(0).getPlateNumber(), "BUS-SBZ-001");
  }

  @Test
  public void test_filterBuses_byType() {
    BusSearchRequest busSearchRequest = new BusSearchRequest();
    busSearchRequest.setType(BusType.DOUBLE_DECKER);

    List<Bus> result = busRepository.filterBuses(busSearchRequest);

    assertEquals(result.size(), 3);
  }

  @Test
  public void test_filterBuses_byColor() {
    BusSearchRequest busSearchRequest = new BusSearchRequest();
    busSearchRequest.setColor(BusColor.GREEN);

    List<Bus> result = busRepository.filterBuses(busSearchRequest);

    assertEquals(result.size(), 4);
  }

  @Test
  public void test_filterBuses_byDepotName_EQUALS() {
    BusSearchRequest busSearchRequest = new BusSearchRequest();
    busSearchRequest.setDepotName(new StringTerm("Grand Central Station", StringFilterType.EQUALS));

    List<Bus> result = busRepository.filterBuses(busSearchRequest);

    assertEquals(result.size(), 1);
  }

  @Test
  public void test_filterBuses_byDepotName_EQUALS_IGNORE_CASE() {
    BusSearchRequest busSearchRequest = new BusSearchRequest();
    busSearchRequest.setDepotName(new StringTerm("Grand CEntrAL Station", StringFilterType.EQUALS_IGNORE_CASE));

    List<Bus> result = busRepository.filterBuses(busSearchRequest);

    assertEquals(result.size(), 1);
  }

  @Test
  public void test_filterBuses_byDepotName_CONTAINS() {
    BusSearchRequest busSearchRequest = new BusSearchRequest();
    busSearchRequest.setDepotName(new StringTerm("Far Far", StringFilterType.CONTAINS));

    List<Bus> result = busRepository.filterBuses(busSearchRequest);

    assertEquals(result.size(), 3);
  }

  @Test
  public void test_filterBuses_byDepotName_CONTAINS_IGNORE_CASE() {
    BusSearchRequest busSearchRequest = new BusSearchRequest();
    busSearchRequest.setDepotName(new StringTerm("Far FAR", StringFilterType.CONTAINS_IGNORE_CASE));

    List<Bus> result = busRepository.filterBuses(busSearchRequest);

    assertEquals(result.size(), 3);
  }
}
