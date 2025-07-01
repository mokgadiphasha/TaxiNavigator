package com.taxiapi.Service.RouteService;

import com.taxiapi.Mapper.TaxiRouteMapperEntityToDto;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.RequestDTO.TaxiRouteDTO;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.IFindRouteService;
import com.taxiapi.Utility.RouteUtilityService;
import com.taxiapi.Utility.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Qualifier("algorithmRouteFinder")
public class BFSAlgorithmRouteFinder implements IFindRouteService {
    private final TaxiRouteRepository repository;
    private final RouteUtilityService util;
    private final ServiceMapper serviceMapper;
    private final TaxiRouteMapperEntityToDto mapperEntityToDto;



    @Autowired
    public BFSAlgorithmRouteFinder(TaxiRouteRepository repository,
                                   RouteUtilityService util,
                                   ServiceMapper serviceMapper, TaxiRouteMapperEntityToDto mapperEntityToDto){
        this.repository = repository;
        this.util = util;
        this.serviceMapper = serviceMapper;
        this.mapperEntityToDto = mapperEntityToDto;
    }


    @Override
        public  TaxiRoutesResponse routeFinder(String fromLocation,
                           String toLocation, TaxiRouteRepository db) {

        HashMap<String, List<String>> graph =  util.generateGraph(
                fromLocation,repository);

        LinkedHashSet<String> visited = new LinkedHashSet<>();
            LinkedList<List<String>> queue = new LinkedList<>();

            queue.add(new ArrayList<>(Arrays.asList(fromLocation)));

            while (!queue.isEmpty()){
                List<String> path = queue.remove();
                String current = path.get(path.size()-1);

                if(current.equals(toLocation)){
                    List<TaxiRoute> route = serviceMapper
                            .mapPathToTaxiRouteResponse(path,repository);

                    List<TaxiRouteDTO> dtoList = mapperEntityToDto.toDto(route);
                    double taxi_fare = util.getTotal(dtoList);

                    return new TaxiRoutesResponse(dtoList,taxi_fare,"R");
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

        return null;
            //TODO: What to return if route not found
    }
}
