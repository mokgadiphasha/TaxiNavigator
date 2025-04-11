package com.taxiapi.Service.RouteService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.IFindRouteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmRouteFinder implements IFindRouteService<String,String, TaxiRoutesResponse> {


    @Override
    public <K> TaxiRoutesResponse routeFinder(String fromLocation, String toLocation, K db) {
        return null;
    }
}
