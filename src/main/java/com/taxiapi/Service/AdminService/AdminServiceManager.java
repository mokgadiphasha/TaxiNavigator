package com.taxiapi.Service.AdminService;

import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Service.GenericCrudServiceIIII;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceManagerIIII extends GenericCrudServiceIIII<TaxiRoute,Long> {
    private TaxiRouteRepository repository ;

    public AdminServiceManagerIIII(TaxiRouteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    


}
