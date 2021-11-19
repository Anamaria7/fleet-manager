package com.flixbus.fleetmanager.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.service.BusService;
import java.util.Collections;
import java.util.List;
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
  public void shouldGetBusById() throws Exception {
    //given
    Integer id = 1;
    BusDto bus = new BusDto();
    String responseBody = new ObjectMapper().writeValueAsString(bus);
    given(busService.getDetailsById(id)).willReturn(bus);

    //when
    mvc.perform(get("/api/bus/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(responseBody));
  }

  @Test
  public void shouldSearchBuses() throws Exception {
    //given
    BusSearchRequest searchRequest = new BusSearchRequest();
    List<BusDto> buses = Collections.singletonList(new BusDto());

    String requestBody = new ObjectMapper().writeValueAsString(searchRequest);
    String responseBody = new ObjectMapper().writeValueAsString(buses);

    given(busService.search(searchRequest)).willReturn(buses);

    //when
    mvc.perform(post("/api/bus/search")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(responseBody));
  }

  @Test
  public void shouldSearchBuses_NOT_FOUND() throws Exception {
    //given
    BusSearchRequest searchRequest = new BusSearchRequest();
    String requestBody = new ObjectMapper().writeValueAsString(searchRequest);
    given(busService.search(searchRequest)).willReturn(null);

    //when
    mvc.perform(post("/api/bus/search")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
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
        .andExpect(status().isCreated())
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

    given(busService.edit(busDto)).willReturn(busDto);

    //when
    mvc.perform(put("/api/bus")
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
