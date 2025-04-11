package com.taxiapi.Controller;

import com.taxiapi.Model.TaxiRoute;
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
        service.create(route);
    }
}
