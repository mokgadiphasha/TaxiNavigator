package com.taxiapi.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NoneExistentRouteResponse extends TaxiRoutesResponse{

    private String message;

}
