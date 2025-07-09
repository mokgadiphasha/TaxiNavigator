package com.taxiapi.Controller;

import com.taxiapi.DTO.TaxiRankDTO;
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
    public void saveTaxiRank(@Validated @RequestBody TaxiRankDTO taxiRank){
        service.saveTaxiRank(taxiRank);
    }

    @PutMapping("/{id}")
    public void updateTaxiRank(@PathVariable Long id,
                               @Validated @RequestBody
                               TaxiRankDTO taxiRank){

        service.updateTaxiRank(id,taxiRank);
    }


    @GetMapping("")
    public TaxiRankResponse findAllTaxiRank(){
        return service.findAllTaxiRanks();
    }
}
