package com.taxiapi.Service.RouteService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.IFindRouteService;
import com.taxiapi.Service.Utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmRouteFinder implements IFindRouteService {

    @Autowired
    private Utility util;

    @Override
    public  TaxiRoutesResponse routeFinder(String fromLocation,
                           String toLocation, TaxiRouteRepository db) {
        return null;
    }
}
