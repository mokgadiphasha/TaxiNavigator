package com.taxiapi.Service.AdminService;

import com.taxiapi.DTO.TaxiRouteCsvDto;
import com.taxiapi.Mapper.TaxiRouteMapperDtoToEntity;
import com.taxiapi.Mapper.TaxiRouteMapperEntityToDto;
import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Repository.TaxiRankRepository;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Repository.TaxiSignRepository;
import com.taxiapi.RequestDTO.TaxiRouteDTO;
import com.taxiapi.Responses.FileResponse;
import com.taxiapi.Responses.TaxiRankResponse;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Responses.TaxiSignResponse;
import com.taxiapi.Service.GenericCrudService;
import com.taxiapi.Utility.CSVUtilityService;
import com.taxiapi.Utility.RouteUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
//TODO: Please find a way to deal with all these duplicates that come from cascade.persist

@Service
public class AdminServiceManager extends GenericCrudService<TaxiRoute,Long> {
    private final RouteUtilityService util;
    private final CSVUtilityService csvUtil;
    private final AdminTaxiRankService taxiRankService;
    private final AdminTaxiSignService taxiSignService;
    private final TaxiRankRepository rankRepository;
    private final TaxiSignRepository signRepository;
    private final TaxiRouteMapperDtoToEntity taxiRouteMapperDtoToEntity;
    private final TaxiRouteMapperEntityToDto routeMapperEntityToDto;


    @Autowired
    public AdminServiceManager(TaxiRouteRepository repository, RouteUtilityService util, CSVUtilityService csvUtil,
                               AdminTaxiRankService taxiRankService, AdminTaxiSignService taxiSignService, TaxiRankRepository rankRepository, TaxiSignRepository signRepository, TaxiRouteMapperDtoToEntity taxiRouteMapperDtoToEntity, TaxiRouteMapperEntityToDto routeMapperEntityToDto) {
        super(repository);
        this.util = util;
        this.csvUtil = csvUtil;
        this.taxiRankService = taxiRankService;
        this.taxiSignService = taxiSignService;
        this.rankRepository = rankRepository;
        this.signRepository = signRepository;
        this.taxiRouteMapperDtoToEntity = taxiRouteMapperDtoToEntity;
        this.routeMapperEntityToDto = routeMapperEntityToDto;
    }


    public TaxiRoutesResponse findAllRoutes(){
        List<TaxiRoute> routes = findAll();

        List<TaxiRouteDTO> dtoRoutes =
                routeMapperEntityToDto.toDto(routes);
        double total = util.getTotal(dtoRoutes);

        return new TaxiRoutesResponse(dtoRoutes,total,"R");
    }


    public FileResponse saveFromCSVFile(MultipartFile file){
        if (file.isEmpty()){
            return new FileResponse("File received but empty." +
                    " Please provide content.");
        }

//TODO: Also check if its possible to check for emptiness apart from first line.

        List<TaxiRouteCsvDto> routeList = csvUtil.csvToObject(file);

        List<TaxiRoute> routes = csvUtil
                .mapCsvToTaxiRoute(routeList);

        createAll(routes);

        return new FileResponse("File received.");
    }


    public ByteArrayResource returnCsvTemplate(){
        return csvUtil.createCsvTemplate();
    }


    public void updateRoute(Long id , TaxiRouteDTO dto){

        String pickUpLocation = dto.getPickUpLocation();
        String pickUpAddress = dto.getPickUpLocationAddress();

        String dropOffLocation = dto.getDropOffLocation();
        String dropOffAddress = dto.getDropOffLocationAddress();

        Long pickUpRankId = util
                .findTaxiRankId(rankRepository
                        ,pickUpLocation,pickUpAddress);

        Long dropOffRankId = util.findTaxiRankId(rankRepository,
                dropOffLocation,dropOffAddress);

        Long taxiSignId = util.findTaxiSignId(signRepository,
                dto.getRouteSignDescription());

        TaxiRoute entity = taxiRouteMapperDtoToEntity.toEntity(dto);


        if(existsById(id)){
            entity.setId(id);
            entity.getFromLocationTaxiRank().setId(pickUpRankId);
            entity.getToLocationTaxiRank().setId(dropOffRankId);
            entity.getTaxiSign().setId(taxiSignId);
            update(entity);
        }
        //TODO: what happens if the id does not exist

    }


    public void deleteRoute(Long id){
        deleteById(id);
    }


    public void saveRoute(TaxiRouteDTO dto){

        String pickUp = dto.getPickUpLocation();
        String dropOff = dto.getDropOffLocation();

        if(util.isNotFromLocationEqualToLocation(
                pickUp, dropOff)){

            util.checkIfAddressAndLocationInDatabase(rankRepository,dto);
            TaxiRoute route = taxiRouteMapperDtoToEntity.toEntity(dto);

            create(route);


        }
        //TODO: figure out the duplication issue with persistence
        // and what happens if the TO and FROM are the same locations
    }


    public void saveTaxiSign(TaxiSign entity){
        taxiSignService.saveTaxiSign(entity);
    }


    public void updateTaxiSign(TaxiSign entity){
        taxiSignService.updateTaxiSign(entity);
    }


    public void deleteTaxiSign(Long id){
        taxiSignService.deleteTaxiSign(id);
    }


    public void saveTaxiRank(TaxiRank entity){
        taxiRankService.saveTaxiRank(entity);
    }

    public TaxiSignResponse findAllTaxiSigns(){
        return taxiSignService.findAllTaxiSigns();
    }


    public void updateTaxiRank(TaxiRank entity){
        taxiRankService.updateTaxiRank(entity);
    }


    public void deleteTaxiRank(Long id){
        taxiRankService.deleteTaxiRank(id);
    }


    public TaxiRankResponse findAllTaxiRanks(){
        return taxiRankService.returnAllTaxiSigns();
    }




    


}
