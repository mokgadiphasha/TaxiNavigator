package com.taxiapi.Controller;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Responses.TaxiRankResponse;
import com.taxiapi.Service.AdminService.AdminServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/taxirank")
public class AdminTaxiRankController {
    @Autowired
    private AdminServiceManager service;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTaxiRank(@Validated @RequestBody TaxiRank taxiRank){
        service.saveTaxiRank(taxiRank);
    }


    @PutMapping("")
    public void updateTaxiRank(@Validated @RequestBody TaxiRank taxiRank){
        service.updateTaxiRank(taxiRank);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaxiRank(@PathVariable Long id){
        service.deleteTaxiRank(id);
    }


    @GetMapping("")
    public TaxiRankResponse findAllTaxiRank(){
        return service.findAllTaxiRanks();
    }
}
