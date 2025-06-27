package com.taxiapi.RequestDTO;

import com.opencsv.bean.CsvBindByName;
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
public class TaxiRouteRequestDTO {

    private String pickUpLocation;

    private String dropOffLocation;

    private String pickUpLocationAddress;

    private String dropOffLocationAddress;

    private Double routeFare;

    private String routeSignDescription;



}
