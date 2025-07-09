package com.taxiapi.Mapper;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.DTO.TaxiRankDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface TaxiRankMapperDtoToEntity {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "locationName", source = "locationName")
    @Mapping(target = "locationAddress", source = "locationAddress")
    TaxiRank toEntity(TaxiRankDTO dto);
}
