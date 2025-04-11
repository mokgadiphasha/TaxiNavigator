package com.taxiapi.Service.RouteService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Responses.TaxiRouteResponse;
import com.taxiapi.Service.IFindRouteService;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRouteFinder implements IFindRouteService<String,String, TaxiRouteResponse> {


    @Override
    public <K> TaxiRouteResponse routeFinder(String fromLocation, String toLocation, K db) {
        return null;
    }
}
