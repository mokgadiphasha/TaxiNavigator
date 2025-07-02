package com.taxiapi.Controller;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.RequestDTO.TaxiRankDTO;
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

    //not sure what the necessity is for this
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTaxiRank(@Validated @RequestBody TaxiRankDTO taxiRank){
        service.saveTaxiRank(taxiRank);
    }

//done
    @PutMapping("/{id}")
    public void updateTaxiRank(@PathVariable Long id,
                               @Validated @RequestBody
                               TaxiRankDTO taxiRank){

        service.updateTaxiRank(id,taxiRank);
    }

//delete is not necessary because it has dependencies
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaxiRank(@PathVariable Long id){
        service.deleteTaxiRank(id);
    }

//done
    @GetMapping("")
    public TaxiRankResponse findAllTaxiRank(){
        return service.findAllTaxiRanks();
    }
}
