package com.taxiapi.Service.RouteService;

import com.taxiapi.Mapper.TaxiRankMapperEntityToDto;
import com.taxiapi.Mapper.TaxiRouteMapperEntityToDto;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.RequestDTO.TaxiRouteDTO;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.IFindRouteService;
import com.taxiapi.Utility.RouteUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("databaseRouteFinder")
public class DatabaseRouteFinder implements IFindRouteService {

    private final RouteUtilityService util;
    private final TaxiRouteMapperEntityToDto mapperEntityToDto;

    @Autowired
    public DatabaseRouteFinder(RouteUtilityService util,
                   TaxiRouteMapperEntityToDto mapperEntityToDto) {
        this.util = util;
        this.mapperEntityToDto = mapperEntityToDto;
    }


    @Override
    public TaxiRoutesResponse routeFinder(String fromLocation,
                          String toLocation, TaxiRouteRepository db) {
        List<TaxiRoute> route = db
                .findByFromLocationAndToLocation(fromLocation,toLocation);

        List<TaxiRouteDTO> dtoList = mapperEntityToDto.toDto(route);
        double totalFare = util.getTotal(dtoList);

        return new TaxiRoutesResponse(dtoList,totalFare,"R");
    }
}
