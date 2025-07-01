package com.taxiapi.Service.AdminService;

import com.taxiapi.Mapper.TaxiSignMapperEntityToDto;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.RequestDTO.TaxiSignDTO;
import com.taxiapi.Responses.TaxiSignResponse;
import com.taxiapi.Service.GenericCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminTaxiSignService extends GenericCrudService<TaxiSign,Long> {

    private final TaxiSignMapperEntityToDto mapperEntityToDto;

    public AdminTaxiSignService(JpaRepository<TaxiSign, Long> repository, TaxiSignMapperEntityToDto mapperEntityToDto) {
        super(repository);
        this.mapperEntityToDto = mapperEntityToDto;
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


    public TaxiSignResponse findAllTaxiSigns(){
        List<TaxiSign> taxiSignList = findAll();
        List<TaxiSignDTO> dtos = mapperEntityToDto.toDto(taxiSignList);

        return new TaxiSignResponse(dtos);
    }




}
