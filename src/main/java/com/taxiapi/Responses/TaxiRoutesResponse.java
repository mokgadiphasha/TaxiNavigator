package com.taxiapi.Responses;

import com.taxiapi.Model.TaxiRoute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TaxiRoutesResponse {
    private List<TaxiRoute> routes;
    private double totalTaxiFare;
}
