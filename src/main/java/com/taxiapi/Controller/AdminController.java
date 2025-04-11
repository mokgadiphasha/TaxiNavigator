package com.taxiapi.Controller;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Responses.TaxiRankResponse;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Responses.TaxiSignResponse;
import com.taxiapi.Service.AdminService.AdminServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminServiceManager service;


    @GetMapping("")
    public TaxiRoutesResponse findAllRoutes(){
        return service.findAllRoutes();
    }


    @PutMapping("")
    public void updateRoute(@Validated @RequestBody TaxiRoute route){
        service.updateRoute(route);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable Long id){
        service.deleteRoute(id);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveRoute(@Validated @RequestBody TaxiRoute route){
        service.saveRoute(route);
    }


    @PostMapping("/taxisign")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTaxiSign(@Validated @RequestBody TaxiSign taxiSign){
        service.saveTaxiSign(taxiSign);
    }


    @PutMapping("/taxisign")
    public void updateTaxiSign(@Validated @RequestBody TaxiSign taxiSign){
        service.updateTaxiSign(taxiSign);
    }


    @DeleteMapping("/taxisign/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaxiSign(@PathVariable Long id){
        service.deleteTaxiSign(id);
    }


    @GetMapping("/taxisign")
    public TaxiSignResponse findAllTaxiSigns(){
        return service.findAllTaxiSigns();
    }


    @PostMapping("/taxirank")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTaxiRank(@Validated @RequestBody TaxiRank taxiRank){
        service.saveTaxiRank(taxiRank);
    }


    @PutMapping("/taxirank")
    public void updateTaxiRank(@Validated @RequestBody TaxiRank taxiRank){
        service.updateTaxiRank(taxiRank);
    }


    @DeleteMapping("/taxirank/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaxiRank(@PathVariable Long id){
        service.deleteTaxiRank(id);
    }


    @GetMapping("/taxirank")
    public TaxiRankResponse findAllTaxiRank(){
        return service.findAllTaxiRanks();
    }
}
