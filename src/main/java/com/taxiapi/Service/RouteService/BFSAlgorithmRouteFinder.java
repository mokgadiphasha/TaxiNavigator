package com.taxiapi.Service.RouteService;

import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.IFindRouteService;
import com.taxiapi.Service.Utility.ServiceUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BFSAlgorithmRouteFinder implements IFindRouteService {
    TaxiRouteRepository repository;
    ServiceUtility util;


    @Autowired
    public BFSAlgorithmRouteFinder(TaxiRouteRepository repository,
                                   ServiceUtility util){
        this.repository = repository;
        this.util = util;
    }


    @Override
    public  TaxiRoutesResponse routeFinder(String fromLocation,
                           String toLocation, TaxiRouteRepository db) {

        System.out.println(util
                .generateGraph(fromLocation,repository).toString());
        return null;
    }
}
