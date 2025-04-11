package com.taxiapi.Service.AdminService;

import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Service.GenericCrudService;
import org.springframework.data.jpa.repository.JpaRepository;

public class AdminTaxiSignService extends GenericCrudService<TaxiSign,Long> {

    public AdminTaxiSignService(JpaRepository<TaxiSign, Long> repository) {
        super(repository);
    }


    public void saveTaxiSign(TaxiSign entity){
        create(entity);
    }


    public void updateTaxiSign(TaxiSign entity){
        update(entity);
    }

    public void deleteTaxiSign(Long id){
        deleteById(id);
    }



}
