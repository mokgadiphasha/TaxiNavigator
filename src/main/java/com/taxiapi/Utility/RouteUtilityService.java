package com.taxiapi.Utility;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RouteUtilityService {
    @Autowired
    ServiceMapper serviceMapper;

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
                        ArrayList<>(serviceMapper
                        .mapToGraphNodes(routes));

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


    public List<TaxiRoute> getDatabaseRoutes(TaxiRouteRepository db,
                                             String fromLocation ){
        return db.findByFromLocation(fromLocation);
    }
}
