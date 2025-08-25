package com.taxiapi.Service.RouteService;

import com.taxiapi.Mapper.TaxiRouteMapperEntityToDto;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.DTO.TaxiRouteDTO;
import com.taxiapi.Responses.NoneExistentRouteResponse;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.IFindRouteService;
import com.taxiapi.Utility.RouteAlgorithmUtility;
import com.taxiapi.Utility.RouteUtilityService;
import com.taxiapi.Utility.ServiceMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Qualifier("algorithmRouteFinder")
public class BFSAlgorithmRouteFinder implements IFindRouteService {
    private final TaxiRouteRepository repository;
    private final RouteAlgorithmUtility routeAlgorithmUtility;
    private final RouteUtilityService routeUtilityService;
    private final ServiceMapperUtility serviceMapperUtility;
    private final TaxiRouteMapperEntityToDto mapperEntityToDto;



    @Autowired
    public BFSAlgorithmRouteFinder(TaxiRouteRepository repository, RouteAlgorithmUtility routeAlgorithmUtility, RouteUtilityService routeUtilityService,
                                   ServiceMapperUtility serviceMapperUtility, TaxiRouteMapperEntityToDto mapperEntityToDto){
        this.repository = repository;
        this.routeAlgorithmUtility = routeAlgorithmUtility;
        this.routeUtilityService = routeUtilityService;
        this.serviceMapperUtility = serviceMapperUtility;
        this.mapperEntityToDto = mapperEntityToDto;
    }


    @Override
        public  TaxiRoutesResponse routeFinder(String fromLocation,
                           String toLocation, TaxiRouteRepository db) {

        HashMap<String, List<String>> graph =  routeAlgorithmUtility.generateGraph(
                fromLocation,repository);

        LinkedHashSet<String> visited = new LinkedHashSet<>();
            LinkedList<List<String>> queue = new LinkedList<>();

            queue.add(new ArrayList<>(Arrays
                    .asList(fromLocation)));

            while (!queue.isEmpty()){
                List<String> path = queue.remove();
                String current = path.get(path.size()-1);

                if(current.equals(toLocation)){
                    List<TaxiRoute> route = serviceMapperUtility
                            .mapPathToTaxiRouteResponse(path,
                                    repository);

                    List<TaxiRouteDTO> dtoList = mapperEntityToDto
                            .toDto(route);
                    double taxi_fare = routeUtilityService
                            .getTotal(dtoList);

                    return new TaxiRoutesResponse(dtoList,
                            taxi_fare,"R");
                }

                if(!visited.contains(current)){
                    visited.add(current);

                    for (String neighbor : graph.get(current) ){
                        List<String> newPath = new ArrayList<>(path);
                        newPath.add(neighbor);

                        queue.add(newPath);
                    }
                }
            }

        return new NoneExistentRouteResponse("We currently do not have " +
                "that route information.");
            //TODO: What to return if route not found - maybe log routes not found
    }
}
