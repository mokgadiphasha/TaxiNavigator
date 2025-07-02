package com.taxiapi.DTO;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaxiRouteCsvDto {

    @CsvBindByName
    private String pickUpLocation;
    @CsvBindByName
    private String pickUpLocationAddress;
    @CsvBindByName
    private String dropOffLocation;
    @CsvBindByName
    private String dropOffLocationAddress;
    @CsvBindByName
    private Double routeFare;
    @CsvBindByName
    private String routeSignDescription;

}
