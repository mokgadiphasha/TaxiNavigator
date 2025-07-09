package com.taxiapi.Service.AdminService;

import com.taxiapi.Exception.ResourceNotFoundException;
import com.taxiapi.Mapper.TaxiSignMapperDtoToEntity;
import com.taxiapi.Mapper.TaxiSignMapperEntityToDto;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.DTO.TaxiSignDTO;
import com.taxiapi.Responses.TaxiSignResponse;
import com.taxiapi.Service.GenericCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminTaxiSignService extends GenericCrudService<TaxiSign,Long> {

    private final TaxiSignMapperEntityToDto mapperEntityToDto;
    private final TaxiSignMapperDtoToEntity mapperDtoToEntity;

    public AdminTaxiSignService(JpaRepository<TaxiSign, Long> repository, TaxiSignMapperEntityToDto mapperEntityToDto, TaxiSignMapperDtoToEntity mapperDtoToEntity) {
        super(repository);
        this.mapperEntityToDto = mapperEntityToDto;
        this.mapperDtoToEntity = mapperDtoToEntity;
    }


    public void updateTaxiSign(Long id,TaxiSignDTO dto){
        if(existsById(id)){
            TaxiSign taxiSign = mapperDtoToEntity.toEntity(dto);
            update(taxiSign);
        } else {
            throw new ResourceNotFoundException("Resource with" +
                    " specified id not found.");
        }
    }


    public TaxiSignResponse findAllTaxiSigns(){
        List<TaxiSign> taxiSignList = findAll();
        List<TaxiSignDTO> dtos = mapperEntityToDto.toDto(taxiSignList);

        return new TaxiSignResponse(dtos);
    }




}
