package com.flixbus.fleetmanager.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.dto.DepotDto;
import com.flixbus.fleetmanager.service.BusService;
import com.flixbus.fleetmanager.service.DepotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepotControllerTest {

  @MockBean
  private DepotService depotService;

  @Autowired
  private MockMvc mvc;

  @Test
  public void shouldGetDepotById() throws Exception {
    //given
    Integer depotId = 1;
    DepotDto depotDto = new DepotDto();

    String responseBody = new ObjectMapper().writeValueAsString(depotDto);

    given(depotService.getById(depotId)).willReturn(depotDto);

    //when
    mvc.perform(get("/api/depot/{id}", depotId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(responseBody));
  }

  @Test
  public void shouldCreateDepot() throws Exception {
    //given
    DepotDto depotDto = new DepotDto();

    String requestBody = new ObjectMapper().writeValueAsString(depotDto);
    String responseBody = new ObjectMapper().writeValueAsString(depotDto);

    given(depotService.create(depotDto)).willReturn(depotDto);

    //when
    mvc.perform(post("/api/depot")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(responseBody));
  }

  @Test
  public void shouldEditDepot() throws Exception {
    //given
    Integer depotId = 1;
    DepotDto depotDto = new DepotDto();
    depotDto.setId(depotId);

    String requestBody = new ObjectMapper().writeValueAsString(depotDto);
    String responseBody = new ObjectMapper().writeValueAsString(depotDto);

    given(depotService.edit(depotId, depotDto)).willReturn(depotDto);

    //when
    mvc.perform(post("/api/depot/{id}", depotId)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(responseBody));
  }

  @Test
  public void shouldDeleteDepot() throws Exception {
    //given
    Integer id = 1;

    doNothing().when(depotService).delete(id);

    //when
    mvc.perform(delete("/api/depot/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
