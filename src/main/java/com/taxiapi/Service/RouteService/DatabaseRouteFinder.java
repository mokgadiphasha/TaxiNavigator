package com.taxiapi.Service.RouteService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.IFindRouteService;
import com.taxiapi.Service.Utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseRouteFinder implements IFindRouteService {

    @Autowired
    private Utility util;
    @Override
    public TaxiRoutesResponse routeFinder(String fromLocation,
                     String toLocation, TaxiRouteRepository db) {

        List<TaxiRoute> route = db
                .findByfromLocationAndtoLocation(fromLocation,toLocation);
        double total_fare = util.getTotal(route);

        return new TaxiRoutesResponse(route,total_fare);
    }
}
