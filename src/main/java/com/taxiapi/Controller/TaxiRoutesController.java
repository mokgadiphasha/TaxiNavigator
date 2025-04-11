package com.taxiapi.Controller;

import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.RouteService.RouteFinderServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/routes/")
public class TaxiRoutesController {
    @Autowired
    private RouteFinderServiceManager service;

    @GetMapping("/{fromLocation}/{toLocation}")
    public TaxiRoutesResponse findRoute(@PathVariable String fromLocation,
                                        @PathVariable String toLocation){
        return service.findRoute(fromLocation,toLocation);
    }

}
