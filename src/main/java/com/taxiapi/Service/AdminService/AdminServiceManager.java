package com.taxiapi.Service.AdminService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.GenericCrudService;
import com.taxiapi.Service.Utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.stream;

@Service
public class AdminServiceManager extends GenericCrudService<TaxiRoute,Long> {
    private TaxiRouteRepository repository ;
    private final Utility util;

    @Autowired
    public AdminServiceManager(TaxiRouteRepository repository,Utility util) {
        super(repository);
        this.repository = repository;
        this.util = util;
    }


    public TaxiRoutesResponse findAllRoutes(){
        List<TaxiRoute> routes = findAll();
        double total = util.getTotal(routes);

        return new TaxiRoutesResponse(routes,total);
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







    


}
