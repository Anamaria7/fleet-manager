package com.flixbus.fleetmanager.repository;

import com.flixbus.fleetmanager.controller.request.BusSearchRequest;
import com.flixbus.fleetmanager.model.Bus;
import java.util.List;

public interface BusRepositoryCustom {

  List<Bus> filterBuses(BusSearchRequest busSearchRequest);

}
