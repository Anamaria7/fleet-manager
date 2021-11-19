package com.flixbus.fleetmanager.dto.mapper;

import com.flixbus.fleetmanager.dto.BusDto;
import com.flixbus.fleetmanager.model.Bus;
import com.flixbus.fleetmanager.model.Depot;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public abstract class BusMapper {

  @Mapping(source = "depot", target = "depotId", qualifiedByName = "depotId")
  public abstract BusDto toDto(Bus bus);
  @Mapping(target = "depot", ignore = true)
  public abstract Bus fromDto(BusDto busDto);

  public abstract List<BusDto> toDtoList(List<Bus> buses);
  public abstract List<Bus> fromDtoList(List<BusDto> buses);

  @Named("depotId")
  public Integer depotId(Depot depot) {
    if (depot != null) {
      return depot.getId();
    }
    return null;
  }

}
