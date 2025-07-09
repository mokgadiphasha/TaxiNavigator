package com.taxiapi.Controller;

import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Responses.TaxiSignResponse;
import com.taxiapi.Service.AdminService.AdminServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/taxisign")
public class AdminTaxiSignController {
    @Autowired
    private AdminServiceManager service;


//still have to think about it
    @PutMapping("")
    public void updateTaxiSign(@Validated @RequestBody TaxiSign taxiSign){
        service.updateTaxiSign(taxiSign);
    }


//done
    @GetMapping("")
    public TaxiSignResponse findAllTaxiSigns(){
        return service.findAllTaxiSigns();
    }
}
