package com.taxiapi.Service.RouteService;

import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.IFindRouteService;
import com.taxiapi.Service.Utility.ServiceUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BFSAlgorithmRouteFinder implements IFindRouteService {

    @Autowired
    private ServiceUtility util;


    @Override
    public  TaxiRoutesResponse routeFinder(String fromLocation,
                           String toLocation, TaxiRouteRepository db) {
        return null;
    }
}
