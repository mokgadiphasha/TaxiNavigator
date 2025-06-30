package com.taxiapi.RequestDTO;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaxiRouteDTO {

    private Long id;

    private String pickUpLocation;

    private String dropOffLocation;

    private String pickUpLocationAddress;

    private String dropOffLocationAddress;

    private Double routeFare;

    private String routeSignDescription;

}
