package com.taxiapi.Mapper;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.RequestDTO.TaxiRouteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface TaxiRouteMapperDtoToEntity {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fromLocation", source = "pickUpLocation")
    @Mapping(target = "toLocation", source = "dropOffLocation")
    @Mapping(target = "fare", source = "routeFare")
    @Mapping(target = "taxiSign.signDescription", source = "routeSignDescription")

    @Mapping(target = "toLocationTaxiRank.locationName",
            source = "dropOffLocation")
    @Mapping(target = "toLocationTaxiRank.locationAddress",
            source = "dropOffLocationAddress")
    @Mapping(target = "fromLocationTaxiRank.locationName",
            source = "pickUpLocation")
    @Mapping(target = "fromLocationTaxiRank.locationAddress",
            source = "pickUpLocationAddress")

    TaxiRoute toEntity(TaxiRouteDTO dto);
}
