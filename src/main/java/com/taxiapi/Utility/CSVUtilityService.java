package com.taxiapi.Utility;


import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.taxiapi.DTO.TaxiRouteCsvDto;
import com.taxiapi.Exception.FileException;
import com.taxiapi.Mapper.TaxiRouteMapperDtoToEntity;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRankRepository;
import com.taxiapi.Repository.TaxiSignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.spi.SyncResolver;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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

            if(util.isFromLocationAndToLocationNotEqual(pickUp, dropOff)){
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


    public TaxiRoute prepareCsvDtoForSave(TaxiRouteCsvDto dto,
                                          TaxiRankRepository rankRepository,
                                          TaxiSignRepository signRepository,
                                          TaxiRouteMapperDtoToEntity mapper){
        TaxiRoute route = mapper
                .toEntity(dto);

        Map<String,Long> result =
                util.validateIfEntitiesInDatabase(rankRepository,
                        signRepository,route);

        route.getFromLocationTaxiRank()
                .setId(result
                        .get("fromTaxiRankId"));

        route.getToLocationTaxiRank()
                .setId(result
                        .get("toTaxiRankId"));

        route.getTaxiSign()
                .setId(result
                        .get("taxiSignId"));
        return route;
    }

//TODO: FIGURE THIS CODE OUT WHEN YOURE NOT TIRED IF IT WORKS
    public boolean validateHeader(MultipartFile file) throws IOException {

        try{
            CSVReader reader = new CSVReader(
                    new InputStreamReader(file.getInputStream()));

            String[] header = reader.readNext();
            List<String> expectedHeaders = List.of("pickUpLocation"
                    ,"pickUpLocationAddress","dropOffLocation",
                    "dropOffLocationAddress","routeFare",
                    "routeSignDescription");

            List<String> headerlist =  Arrays.stream(header)
                    .map(String::trim)
                    .toList();

           List<String> expected =  expectedHeaders.stream()
                   .map(String::trim)
                   .toList();

            return !expected.equals(headerlist);

        } catch (CsvValidationException e) {
            throw new FileException("Failed to process file.");
        }


    }

}
