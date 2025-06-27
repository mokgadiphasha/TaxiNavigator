package com.taxiapi.DTO;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxiRouteCsvDto {

    @CsvBindByName
    private String pickUpLocationName;
    @CsvBindByName
    private String pickUpLocationAddress;
    @CsvBindByName
    private String dropOffLocationName;
    @CsvBindByName
    private String dropOffLocationAddress;
    @CsvBindByName
    private String startLocation;
    @CsvBindByName
    private String endLocation;
    @CsvBindByName
    private Double routeFare;
    @CsvBindByName
    private String routeSignDescription;

}
