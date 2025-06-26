package com.taxiapi.Utility;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.taxiapi.DTO.TaxiRouteDTO;
import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Model.TaxiSign;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVUtilityService {

    public List<TaxiRouteDTO> csvToObject(MultipartFile file){
        try{
            InputStreamReader reader =
                    new InputStreamReader(file.getInputStream());

            CsvToBean<TaxiRouteDTO> builder =
                    new CsvToBeanBuilder<TaxiRouteDTO>(reader)
                    .withType(TaxiRouteDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return builder.parse();

        } catch (IOException e) {
            //Todo change using error handling mechanisms in spring
            throw new RuntimeException(e);
        }

    }


    public List<TaxiRoute> mapCsvToTaxiRoute(List<TaxiRouteDTO> routes){
        List<TaxiRoute> routeList = new ArrayList<>();

        for (TaxiRouteDTO route : routes){
            TaxiRoute taxiRoute = new TaxiRoute();
            TaxiRank taxiRank = new TaxiRank();
            TaxiSign taxiSign = new TaxiSign();

            taxiSign.setSignDescription(route
                    .getRouteSignDescription());

            taxiRank.setDropOffRankAddress(route
                    .getDropOffLocationAddress());

            taxiRank.setDropOffRankName(route
                    .getDropOffLocationName());

            taxiRank.setPickUpRankName(route
                    .getPickUpLocationName());

            taxiRank.setPickUpRankAddress(route
                    .getPickUpLocationAddress());

            taxiRoute.setFare(route.getRouteFare());
            taxiRoute.setToLocation(route.getEndLocation());
            taxiRoute.setFromLocation(route.getStartLocation());
            taxiRoute.setTaxiSign(taxiSign);
            taxiRoute.setTaxiRank(taxiRank);

            routeList.add(taxiRoute);
        }
        return routeList;
    }


    public ByteArrayResource createCsvTemplate(){
        String templateContent = "startLocation,endLocation," +
                "routeFare,pickUpLocationName,pickUpLocationAddress" +
                ",dropOffLocationName," +
                "dropOffLocationAddress,routeSignDescription\r\n";

        return new ByteArrayResource(templateContent.getBytes());

    }

}
