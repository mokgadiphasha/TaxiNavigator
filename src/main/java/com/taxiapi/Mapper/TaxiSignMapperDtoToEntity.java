package com.taxiapi.Mapper;

import com.taxiapi.DTO.TaxiSignDTO;
import com.taxiapi.Model.TaxiSign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface TaxiSignMapperDtoToEntity {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "signDescription", source = "signDescription")
    TaxiSign toEntity(TaxiSignDTO dto);
}
