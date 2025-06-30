package com.taxiapi.Utility;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.taxiapi.DTO.TaxiRouteCsvDto;
import com.taxiapi.Mapper.TaxiRouteMapperDtoToEntity;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVUtilityService {

    @Autowired
    RouteUtilityService util;
    @Autowired
    TaxiRouteMapperDtoToEntity mapperDtoToEntity;

    public List<TaxiRouteCsvDto> csvToObject(MultipartFile file){
        try{
            InputStreamReader reader =
                    new InputStreamReader(file.getInputStream());

            CsvToBean<TaxiRouteCsvDto> builder =
                    new CsvToBeanBuilder<TaxiRouteCsvDto>(reader)
                    .withType(TaxiRouteCsvDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return builder.parse();

        } catch (IOException e) {
            //Todo change using error handling mechanisms in spring
            throw new RuntimeException(e);
        }

    }


    public List<TaxiRoute> mapCsvToTaxiRoute(List<TaxiRouteCsvDto> routes){
        List<TaxiRoute> routeList = new ArrayList<>();

        for (TaxiRouteCsvDto dto: routes){

            String pickUp = dto.getPickUpLocation();
            String dropOff = dto.getDropOffLocation();

            if(util.isNotFromLocationEqualToLocation(pickUp, dropOff)){
                TaxiRoute entity = mapperDtoToEntity.toEntity(dto);
                routeList.add(entity);
            }
        }

        return routeList;
        //TODO: Find a way to save each item by ensuring
        // no duplicates exist in the process cascade.perisist issue.
    }


    public ByteArrayResource createCsvTemplate(){
        String templateContent =
                "pickUpLocation,pickUpLocationAddress" +
                ",dropOffLocation," +
                "dropOffLocationAddress,routeFare," +
                        "routeSignDescription\r\n";

        return new ByteArrayResource(templateContent.getBytes());

    }

}
