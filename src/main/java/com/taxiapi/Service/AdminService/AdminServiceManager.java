package com.taxiapi.Service.AdminService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceManager extends GenericCrudService<TaxiRoute,Long> {
    private TaxiRouteRepository repository ;

    public AdminServiceManager(TaxiRouteRepository repository) {
        super(repository);
        this.repository = repository;
    }



    


}
