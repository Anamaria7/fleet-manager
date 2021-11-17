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
import com.flixbus.fleetmanager.service.BusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BusControllerTest {

  @MockBean
  private BusService busService;

  @Autowired
  private MockMvc mvc;

  @Test
  public void shouldGetBusByPlateNumber() throws Exception {
    //given
    String plateNumber = "BUS";
    BusDto busDto = new BusDto();

    String responseBody = new ObjectMapper().writeValueAsString(busDto);

    given(busService.getDetailsByPlateNumber(plateNumber)).willReturn(busDto);

    //when
    mvc.perform(get("/api/bus")
        .param("plateNumber", plateNumber)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(responseBody));
  }

  @Test
  public void shouldCreateBus() throws Exception {
    //given
    BusDto busDto = new BusDto();

    String requestBody = new ObjectMapper().writeValueAsString(busDto);
    String responseBody = new ObjectMapper().writeValueAsString(busDto);

    given(busService.create(busDto)).willReturn(busDto);

    //when
    mvc.perform(post("/api/bus")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(responseBody));
  }

  @Test
  public void shouldEditBus() throws Exception {
    //given
    Integer id = 1;
    BusDto busDto = new BusDto();
    busDto.setId(id);

    String requestBody = new ObjectMapper().writeValueAsString(busDto);
    String responseBody = new ObjectMapper().writeValueAsString(busDto);

    given(busService.edit(id, busDto)).willReturn(busDto);

    //when
    mvc.perform(post("/api/bus/{id}", id)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(responseBody));
  }

  @Test
  public void shouldDeleteBus() throws Exception {
    //given
    Integer id = 1;

    doNothing().when(busService).delete(id);

    //when
    mvc.perform(delete("/api/bus/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
