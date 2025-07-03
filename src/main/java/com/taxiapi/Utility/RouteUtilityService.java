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


    public boolean isFromLocationAndToLocationNotEqual(
                                              String pickUp, String dropOff){

        return !pickUp.equalsIgnoreCase(dropOff);

    }


    public Map<String, Long> validateIfEntitiesInDatabase(
            TaxiRankRepository rankRepository,
            TaxiSignRepository  signRepository,
            TaxiRoute route){

        Map<String,Long> result = new HashMap<>();

        String pickUpLocation = route.getFromLocation();
        String pickUpAddress = route.getFromLocationTaxiRank()
                .getLocationAddress();

        String dropOffLocation = route.getToLocation();
        String dropOffAddress = route.getToLocationTaxiRank()
                .getLocationAddress();

        String signDescription = route.getTaxiSign()
                .getSignDescription();

        boolean resultA = rankRepository
                .existsByLocationNameAndLocationAddress(pickUpLocation,
                pickUpAddress);

        boolean resultB = rankRepository
                .existsByLocationNameAndLocationAddress(dropOffLocation,
                        dropOffAddress);

        boolean resultC = signRepository
                .existsBySignDescription(signDescription);

        if(!resultA)
            rankRepository
                .save(new TaxiRank(pickUpLocation,pickUpAddress));

        if(!resultB)
            rankRepository
                .save(new TaxiRank(dropOffLocation,dropOffAddress));

        if(!resultC) signRepository
                .save(new TaxiSign(signDescription));

        result.put("taxiSignId",signRepository
                .findBySignDescription(
                        signDescription)
                .getId());

        result.put("fromTaxiRankId", rankRepository
                .findByLocationNameAndLocationAddress(
                        pickUpLocation,pickUpAddress)
                .getId());

        result.put("toTaxiRankId", rankRepository
                .findByLocationNameAndLocationAddress(
                        dropOffLocation,dropOffAddress)
                .getId());

        return result;

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
