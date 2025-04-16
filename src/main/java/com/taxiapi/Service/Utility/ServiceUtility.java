package com.taxiapi.Service.Utility;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        List<String> neighbours = mapToGraphNodes(routes);
        graph.putIfAbsent(fromLocation,neighbours);


        for (int i = 0; i < neighbours.size(); i++){
            fromLocation = neighbours.get(i);
            routes = getDatabaseRoutes(db,fromLocation);
            List<String> newNeighbour = mapToGraphNodes(routes);
            graph.putIfAbsent(fromLocation,newNeighbour);
            neighbours.addAll(newNeighbour);
        }

        return graph;

    }


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


    public List<TaxiRoute> getDatabaseRoutes(TaxiRouteRepository db,
                                             String fromLocation ){
        return db.findByFromLocation(fromLocation);
    }



}
