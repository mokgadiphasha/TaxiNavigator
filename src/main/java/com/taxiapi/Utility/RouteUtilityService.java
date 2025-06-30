package com.taxiapi.Utility;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Repository.TaxiRankRepository;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Repository.TaxiSignRepository;
import com.taxiapi.RequestDTO.TaxiRouteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RouteUtilityService {
    @Autowired
    ServiceMapper serviceMapper;

    public double getTotal(List<TaxiRouteDTO> routes){
        return routes.stream()
                .mapToDouble(TaxiRouteDTO::getRouteFare)
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


    public boolean isNotFromLocationEqualToLocation(
                                              String pickUp, String dropOff){

        return !pickUp.equalsIgnoreCase(dropOff);

    }


    public void checkIfAddressAndLocationInDatabase(
            TaxiRankRepository repository,
            TaxiRouteDTO dto){

//        String pickUpLocation = dto.getPickUpLocation();
//        String pickUpAddress = dto.getPickUpLocationAddress();
//
//        String dropOffLocation = dto.getDropOffLocation();
//        String dropOffAddress = dto.getDropOffLocationAddress();
//
//        boolean resultA = repository
//                .existsByLocationNameAndLocationAddress(pickUpLocation,
//                pickUpAddress);
//
//        boolean resultB = repository
//                .existsByLocationNameAndLocationAddress(dropOffLocation,
//                        dropOffAddress);
//
//        if(!resultA) {
//            System.out.println("Pickup location does not exist but has been added.");
//            repository
//                .save(new TaxiRank(pickUpLocation,pickUpAddress));}
//        if(!resultB) {
//            System.out.println("Drop off location does not exist but has been added.");
//
//            repository
//                .save(new TaxiRank(dropOffLocation,dropOffAddress));}


    }


    public Long findTaxiRankId(TaxiRankRepository repository,
                               String locationName, String address){
        TaxiRank rank =  repository
                .findByLocationNameAndLocationAddress(
                        locationName,address);
        return rank.getId();

    }


    public Long findTaxiSignId(TaxiSignRepository repository,
                               String signDescription){
        TaxiSign taxiSign = repository
                .findBySignDescription(signDescription);

        return taxiSign.getId();
    }



}
