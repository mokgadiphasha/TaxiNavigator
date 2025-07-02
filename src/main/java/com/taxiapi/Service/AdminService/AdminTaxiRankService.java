package com.taxiapi.Service.AdminService;

import com.taxiapi.Mapper.TaxiRankMapperDtoToEntity;
import com.taxiapi.Mapper.TaxiRankMapperEntityToDto;
import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Repository.TaxiRankRepository;
import com.taxiapi.RequestDTO.TaxiRankDTO;
import com.taxiapi.Responses.TaxiRankResponse;
import com.taxiapi.Responses.TaxiSignResponse;
import com.taxiapi.Service.GenericCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminTaxiRankService extends GenericCrudService<TaxiRank,Long> {

    private final TaxiRankMapperDtoToEntity mapperDtoToEntity;
    private final TaxiRankMapperEntityToDto mapperEntityToDto;
    private final TaxiRankRepository rankRepository;

    public AdminTaxiRankService(JpaRepository<TaxiRank, Long> repository, TaxiRankMapperDtoToEntity mapperDtoToEntity, TaxiRankMapperEntityToDto mapperEntityToDto, TaxiRankRepository rankRepository) {
        super(repository);
        this.mapperDtoToEntity = mapperDtoToEntity;
        this.mapperEntityToDto = mapperEntityToDto;
        this.rankRepository = rankRepository;
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
        }
//        TODO: What happens if the id does not exist

    }


    public void deleteTaxiRank(Long id){
        deleteById(id);
    }


    public TaxiRankResponse returnAllTaxiSigns(){
        List<TaxiRank> taxiRanks = findAll();
        List<TaxiRankDTO> dtos = mapperEntityToDto.toDto(taxiRanks);

        return new TaxiRankResponse(dtos);
    }

}
