package com.taxiapi.Service.Utility;

import com.taxiapi.Model.TaxiRoute;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Utility {

    public double getTotal(List<TaxiRoute> routes){
        return routes.stream()
                .mapToDouble(TaxiRoute::getFare)
                .sum();
    }



}
