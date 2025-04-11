package com.taxiapi.Service.AdminService;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Responses.TaxiRankResponse;
import com.taxiapi.Responses.TaxiSignResponse;
import com.taxiapi.Service.GenericCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class AdminTaxiRankService extends GenericCrudService<TaxiRank,Long> {

    public AdminTaxiRankService(JpaRepository<TaxiRank, Long> repository) {
        super(repository);
    }


    public void saveTaxiRank(TaxiRank entity){
        create(entity);
    }


    public void updateTaxiRank(TaxiRank entity){
        update(entity);
    }


    public void deleteTaxiRank(Long id){
        deleteById(id);
    }


    public TaxiRankResponse returnAllTaxiSigns(){
        return new TaxiRankResponse(findAll());
    }

}
