package com.taxiapi.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaxiRouteDTO {

    private String pickUpLocation;

    private String dropOffLocation;

    private String pickUpLocationAddress;

    private String dropOffLocationAddress;

    private Double routeFare;

    private String routeSignDescription;



}
