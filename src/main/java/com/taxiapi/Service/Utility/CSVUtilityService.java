package com.taxiapi.Service.Utility;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.taxiapi.Model.TaxiRoute;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CSVUtilityService {

    public List<TaxiRoute> csvToObject(MultipartFile file){
        try{
            InputStreamReader reader =
                    new InputStreamReader(file.getInputStream());

            CsvToBean<TaxiRoute> builder =
                    new CsvToBeanBuilder<TaxiRoute>(reader)
                    .withType(TaxiRoute.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return builder.parse();

        } catch (IOException e) {
            //change using error handling mechanisms in spring
            throw new RuntimeException(e);
        }

    }

}
