package com.taxiapi.Service.RouteService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Service.GenericCrudService;
import com.taxiapi.Service.RouteFinder;

public class DirectRouteFinder extends GenericCrudService<TaxiRoute,Long>
        implements RouteFinder<TaxiRoute,String> {
    private TaxiRouteRepository repository;

    public DirectRouteFinder(TaxiRouteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public TaxiRoute routeFinder(String fromLocation, String toLocation) {

        return null;
    }
}
