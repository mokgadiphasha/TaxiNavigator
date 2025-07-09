package com.taxiapi.Mapper;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.DTO.TaxiRankDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface TaxiRankMapperEntityToDto {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "locationName", source = "locationName")
    @Mapping(target = "locationAddress", source = "locationAddress")
    TaxiRankDTO toDto(TaxiRank entity);
    List<TaxiRankDTO> toDto(List<TaxiRank> entities);
}
