package com.taxiapi.Service;

import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;

public interface IFindRouteService{
    TaxiRoutesResponse routeFinder (String fromLocation, String toLocation, TaxiRouteRepository db);
}
