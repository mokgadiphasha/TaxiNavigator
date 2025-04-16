package com.taxiapi.Service.RouteService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.GenericCrudService;
import com.taxiapi.Service.IFindRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RouteFinderServiceManager extends GenericCrudService<TaxiRoute,Long> {
    private TaxiRouteRepository repository;
    private IFindRouteService algorithmRouteFinder;
    private IFindRouteService databaseRouteFinder;

    @Autowired
    public RouteFinderServiceManager(TaxiRouteRepository repository,
                                     @Qualifier("algorithmRouteFinder")
              IFindRouteService algorithmRouteFinder ,
                                     @Qualifier("databaseRouteFinder")
             IFindRouteService databaseRouteFinder) {
                    super(repository);
                    this.repository = repository;
    }


    public TaxiRoutesResponse findRoute(String fromLocation, String toLocation){
        TaxiRoutesResponse route = databaseRouteFinder
                .routeFinder(fromLocation,toLocation, repository);

         if(route.getRoutes().isEmpty()){
             route = algorithmRouteFinder
                     .routeFinder(fromLocation,toLocation,repository);
         }

        return route;
    }








}
