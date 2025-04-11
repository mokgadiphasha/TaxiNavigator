package com.taxiapi.Service.RouteService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRouteResponse;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.GenericCrudService;
import com.taxiapi.Service.IFindRouteService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RouteFinderServiceManager extends GenericCrudService<TaxiRoute,Long> {
    private TaxiRouteRepository repository;
    private IFindRouteService<String,String, TaxiRoutesResponse> algorithmRouteFinder;
    private IFindRouteService<String,String, TaxiRouteResponse> databaseRouteFinder;

    public RouteFinderServiceManager(TaxiRouteRepository repository,
                                     @Qualifier("algorithmRouteFinder")
              IFindRouteService<String,String,TaxiRoutesResponse> algorithmRouteFinder ,
                                     @Qualifier("databaseRouteFinder")
             IFindRouteService<String,String,TaxiRouteResponse> databaseRouteFinder) {
                    super(repository);
                    this.repository = repository;
    }

}
