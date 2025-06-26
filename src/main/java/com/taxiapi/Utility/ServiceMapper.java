package com.taxiapi.Utility;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceMapper {

    public List<TaxiRoute> mapPathToTaxiRouteResponse(List<String> path,
                                                      TaxiRouteRepository db){
        List<TaxiRoute> routes =  new ArrayList<>();

        for (int i = 0; i < path.size() - 1; i++){
            String currentLocation = path.get(i);
            String nextLocation = path.get(i+1);

            List<TaxiRoute> taxiRoute = db
                    .findByFromLocationAndToLocation(currentLocation
                            ,nextLocation);

            routes.add(taxiRoute.get(0));
        }

        return routes;
    }


    public List<String> mapToGraphNodes(List<TaxiRoute> routes){
        return routes.stream()
                .map(TaxiRoute::getToLocation)
                .toList();
    }
}
