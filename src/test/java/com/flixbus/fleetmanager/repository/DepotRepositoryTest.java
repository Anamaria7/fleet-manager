package com.flixbus.fleetmanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.flixbus.fleetmanager.model.Depot;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class DepotRepositoryTest {

  @Resource
  private DepotRepository depotRepository;

  @Test
  public void givenDepot_whenSave_thenGetOk() {
    Depot depot = new Depot();
    depot.setName("Here");
    depot.setCapacity(20);
    Depot result = depotRepository.save(depot);

    assertEquals(result.getName(), depot.getName());
  }
}
