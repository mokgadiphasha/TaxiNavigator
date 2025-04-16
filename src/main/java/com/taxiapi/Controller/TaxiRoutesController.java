package com.taxiapi.Controller;

import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.RouteService.RouteFinderServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/routes")
public class TaxiRoutesController {
    @Autowired
    private RouteFinderServiceManager service;

    @GetMapping("")
    public TaxiRoutesResponse findRoute(@RequestParam String from,
                                        @RequestParam String to){
        return service.findRoute(from,to);
    }

}
