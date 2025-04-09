package com.taxiapi.Service.RouteService;

import com.taxiapi.Service.IFindRouteService;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmRouteFinder implements IFindRouteService<String,String> {


    @Override
    public <K> String routeFinder(String fromLocation, String toLocation, K db) {
        return "";
    }
}
