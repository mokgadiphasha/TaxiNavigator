package com.taxiapi.Service.RouteService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.IFindRouteService;
import com.taxiapi.Service.Utility.RouteUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("databaseRouteFinder")
public class DatabaseRouteFinder implements IFindRouteService {

    @Autowired
    private RouteUtilityService util;


    @Override
    public TaxiRoutesResponse routeFinder(String fromLocation,
                     String toLocation, TaxiRouteRepository db) {

        List<TaxiRoute> route = db
                .findByFromLocationAndToLocation(fromLocation,toLocation);
        double totalFare = util.getTotal(route);

        return new TaxiRoutesResponse(route,totalFare,"R");
    }
}
