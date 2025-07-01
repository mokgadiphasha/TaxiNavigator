package com.taxiapi.Mapper;

import com.taxiapi.Model.TaxiSign;
import com.taxiapi.RequestDTO.TaxiSignDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface TaxiSignMapperEntityToDto {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "signDescription", source = "signDescription")
    TaxiSignDTO toDto(TaxiSign entity);
    List<TaxiSignDTO> toDto(List<TaxiSign> entities);
}
