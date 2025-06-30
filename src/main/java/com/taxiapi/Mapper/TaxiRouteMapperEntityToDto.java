package com.taxiapi.Mapper;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.RequestDTO.TaxiRouteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface TaxiRouteMapperEntityToDto {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "pickUpLocation", source = "fromLocation")
    @Mapping(target = "dropOffLocation", source = "toLocation")
    @Mapping(target = "routeFare", source = "fare")

    @Mapping(target = "routeSignDescription",
            source = "taxiSign.signDescription")
    @Mapping(target = "dropOffLocationAddress",
            source = "toLocationTaxiRank.locationAddress")
    @Mapping(target = "pickUpLocationAddress",
            source = "fromLocationTaxiRank.locationAddress")

    TaxiRouteDTO toDto(TaxiRoute entity);

    List<TaxiRouteDTO> toDto(List<TaxiRoute> routes);
}
