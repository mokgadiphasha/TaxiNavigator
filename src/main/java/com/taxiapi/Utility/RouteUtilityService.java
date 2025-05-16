package com.taxiapi.Utility;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RouteUtilityService {

    public double getTotal(List<TaxiRoute> routes){
        return routes.stream()
                .mapToDouble(TaxiRoute::getFare)
                .sum();
    }


    public HashMap<String,List<String>> generateGraph(String fromLocation,
                                                      TaxiRouteRepository db){
        HashMap<String,List<String>> graph = new HashMap<>();
        Queue<String> toVisit = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        toVisit.add(fromLocation);

        while(!toVisit.isEmpty()){
            String currentLocation = toVisit.poll();

            if(!visited.contains(currentLocation)){

                visited.add(currentLocation);
                List<TaxiRoute> routes = getDatabaseRoutes(db,currentLocation);
                List<String> neighbours = new
                        ArrayList<>(mapToGraphNodes(routes));

                graph.putIfAbsent(currentLocation,neighbours);

                for (String neighbour: neighbours){
                    if(!visited.contains(neighbour)){
                        toVisit.add(neighbour);
                    }
                }

            }

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
