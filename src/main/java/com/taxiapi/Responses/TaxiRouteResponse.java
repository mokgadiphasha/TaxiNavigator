package com.taxiapi.Responses;

import com.taxiapi.Model.TaxiRoute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TaxiRouteResponse {
    private TaxiRoute route;
    private double totalTaxiFare;
}
