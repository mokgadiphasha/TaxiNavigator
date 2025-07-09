package com.taxiapi.Service.AdminService;

import com.taxiapi.Exception.ResourceNotFoundException;
import com.taxiapi.Mapper.TaxiRankMapperDtoToEntity;
import com.taxiapi.Mapper.TaxiRankMapperEntityToDto;
import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Repository.TaxiRankRepository;
import com.taxiapi.DTO.TaxiRankDTO;
import com.taxiapi.Responses.TaxiRankResponse;
import com.taxiapi.Service.GenericCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminTaxiRankService extends GenericCrudService<TaxiRank,Long> {

    private final TaxiRankMapperDtoToEntity mapperDtoToEntity;
    private final TaxiRankMapperEntityToDto mapperEntityToDto;

    public AdminTaxiRankService(JpaRepository<TaxiRank, Long> repository,
                                TaxiRankMapperDtoToEntity mapperDtoToEntity,
                                TaxiRankMapperEntityToDto mapperEntityToDto) {
        super(repository);
        this.mapperDtoToEntity = mapperDtoToEntity;
        this.mapperEntityToDto = mapperEntityToDto;
    }


    public void saveTaxiRank(TaxiRankDTO dto){
        TaxiRank taxiRank = mapperDtoToEntity.toEntity(dto);
        create(taxiRank);
    }


    public void updateTaxiRank(Long id,TaxiRankDTO dto){

        if (existsById(id)){
            TaxiRank rank = mapperDtoToEntity.toEntity(dto);
            rank.setId(id);
            update(rank);
        } else {
            throw new ResourceNotFoundException("Resource with" +
                    " specified id not found.");
        }
//        TODO: What happens if the id does not exist

    }


    public TaxiRankResponse returnAllTaxiSigns(){
        List<TaxiRank> taxiRanks = findAll();
        List<TaxiRankDTO> dtos = mapperEntityToDto.toDto(taxiRanks);

        return new TaxiRankResponse(dtos);
    }

}
