package com.taxiapi.Service.Utility;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceUtility {

    public double getTotal(List<TaxiRoute> routes){
        return routes.stream()
                .mapToDouble(TaxiRoute::getFare)
                .sum();
    }


    public HashMap<String,List<String>> generateGraph(String fromLocation,
                                                      TaxiRouteRepository db){
        HashMap<String,List<String>> graph = new HashMap<>();

        List<TaxiRoute> routes = getDatabaseRoutes(db,fromLocation);
        List<String> neighbors = insertGraphNodes(routes);
        graph.putIfAbsent(fromLocation,neighbors);


        for (int i = 0; i < neighbors.size(); i++){
            fromLocation = neighbors.get(i);
            routes = getDatabaseRoutes(db,fromLocation);
            List<String> new_neighbors = insertGraphNodes(routes);
            graph.putIfAbsent(fromLocation,new_neighbors);
            neighbors.addAll(new_neighbors);
        }

        return graph;

    }


    public List<String> insertGraphNodes(List<TaxiRoute> routes){
        return routes.stream()
                .map(TaxiRoute::getToLocation)
                .toList();
    }

    public List<TaxiRoute> getDatabaseRoutes(TaxiRouteRepository db,
                                             String fromLocation ){
        return db.findByFromLocation(fromLocation);
    }



}
