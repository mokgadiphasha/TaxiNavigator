package com.taxiapi.Service.RouteService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Service.GenericCrudService;
import com.taxiapi.Service.IFindRouteService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RouteFinderServiceManager extends GenericCrudService<TaxiRoute,Long> {
    private TaxiRouteRepository repository;
    private IFindRouteService<String,String> algorithmRouteFinder;
    private IFindRouteService<String,String> databaseRouteFinder;

    public RouteFinderServiceManager(TaxiRouteRepository repository,
                                     @Qualifier("algorithmRouteFinder")
              IFindRouteService<String,String> algorithmRouteFinder ,
                                     @Qualifier("databaseRouteFinder") IFindRouteService<String,String>
              databaseRouteFinder) {
                    super(repository);
                    this.repository = repository;
    }

}
