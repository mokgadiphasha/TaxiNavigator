package com.taxiapi.Controller;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Responses.TaxiRoutesResponse;
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


    @PostMapping("/taxiSign")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTaxiSign(@Validated @RequestBody TaxiSign taxiSign){
        service.saveTaxiSign(taxiSign);
    }


    @PutMapping("/taxiSign")
    public void updateTaxiSign(@Validated @RequestBody TaxiSign taxiSign){
        service.updateTaxiSign(taxiSign);
    }


    @DeleteMapping("/taxiSign/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaxiSign(@PathVariable Long id){
        service.deleteTaxiSign(id);
    }


    @PostMapping("/taxiRank")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTaxiRank(@Validated @RequestBody TaxiRank taxiRank){
        service.saveTaxiRank(taxiRank);
    }


    @PutMapping("/taxiRank")
    public void updateTaxiRank(@Validated @RequestBody TaxiRank taxiRank){
        service.updateTaxiRank(taxiRank);
    }


    @DeleteMapping("/taxiRank/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaxiRank(@PathVariable Long id){
        service.deleteTaxiRank(id);
    }
}
