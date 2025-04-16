package com.taxiapi.Service.AdminService;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.EmptyFileResponse;
import com.taxiapi.Responses.TaxiRankResponse;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Responses.TaxiSignResponse;
import com.taxiapi.Service.GenericCrudService;
import com.taxiapi.Service.Utility.CSVUtilityService;
import com.taxiapi.Service.Utility.ServiceUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.Arrays.stream;

@Service
public class AdminServiceManager extends GenericCrudService<TaxiRoute,Long> {
    private final ServiceUtility util;
    private final CSVUtilityService csvUtil;
    private final AdminTaxiRankService taxiRankService;
    private final AdminTaxiSignService taxiSignService;


    @Autowired
    public AdminServiceManager(TaxiRouteRepository repository, ServiceUtility util, CSVUtilityService csvUtil,
                               AdminTaxiRankService taxiRankService, AdminTaxiSignService taxiSignService) {
        super(repository);
        this.util = util;
        this.csvUtil = csvUtil;
        this.taxiRankService = taxiRankService;
        this.taxiSignService = taxiSignService;
    }


    public TaxiRoutesResponse findAllRoutes(){
        List<TaxiRoute> routes = findAll();
        double total = util.getTotal(routes);

        return new TaxiRoutesResponse(routes,total);
    }


    public EmptyFileResponse saveFromCSVFile(MultipartFile file){
        if (file.isEmpty()){
            return new EmptyFileResponse("File received but empty." +
                    " Please provide content.");
        }
        List<TaxiRoute> routes = csvUtil.csvToObject(file);
        createAll(routes);
        return new EmptyFileResponse("File received.");
    }


    public InputStreamResource returnCsvTemplate(){
        return csvUtil.createCsvTemplate();
    }


    public void updateRoute(TaxiRoute route){
        update(route);
    }


    public void deleteRoute(Long id){
        deleteById(id);
    }


    public void saveRoute(TaxiRoute route){
        create(route);
    }


    public void saveTaxiSign(TaxiSign entity){
        taxiSignService.saveTaxiSign(entity);
    }


    public void updateTaxiSign(TaxiSign entity){
        taxiSignService.updateTaxiSign(entity);
    }


    public void deleteTaxiSign(Long id){
        taxiSignService.deleteTaxiSign(id);
    }


    public void saveTaxiRank(TaxiRank entity){
        taxiRankService.saveTaxiRank(entity);
    }

    public TaxiSignResponse findAllTaxiSigns(){
        return taxiSignService.findAllTaxiSigns();
    }


    public void updateTaxiRank(TaxiRank entity){
        taxiRankService.updateTaxiRank(entity);
    }


    public void deleteTaxiRank(Long id){
        taxiRankService.deleteTaxiRank(id);
    }


    public TaxiRankResponse findAllTaxiRanks(){
        return taxiRankService.returnAllTaxiSigns();
    }




    


}
