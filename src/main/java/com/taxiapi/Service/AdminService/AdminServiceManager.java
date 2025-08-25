package com.taxiapi.Service.AdminService;

import com.taxiapi.DTO.TaxiRouteCsvDto;
import com.taxiapi.DTO.TaxiSignDTO;
import com.taxiapi.Exception.DuplicateResourceException;
import com.taxiapi.Exception.FileException;
import com.taxiapi.Exception.ResourceNotFoundException;
import com.taxiapi.Mapper.TaxiRouteMapperDtoToEntity;
import com.taxiapi.Mapper.TaxiRouteMapperEntityToDto;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Repository.TaxiRankRepository;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Repository.TaxiSignRepository;
import com.taxiapi.DTO.TaxiRankDTO;
import com.taxiapi.DTO.TaxiRouteDTO;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private final TaxiRouteRepository routeRepository;


    @Autowired
    public AdminServiceManager(TaxiRouteRepository repository, RouteUtilityService util, CSVUtilityService csvUtil,
                               AdminTaxiRankService taxiRankService,
                               AdminTaxiSignService taxiSignService,
                               TaxiRankRepository rankRepository,
                               TaxiSignRepository signRepository,
                               TaxiRouteMapperDtoToEntity
                                           taxiRouteMapperDtoToEntity
            , TaxiRouteMapperEntityToDto routeMapperEntityToDto,
                               TaxiRouteRepository routeRepository) {
        super(repository);
        this.util = util;
        this.csvUtil = csvUtil;
        this.taxiRankService = taxiRankService;
        this.taxiSignService = taxiSignService;
        this.rankRepository = rankRepository;
        this.signRepository = signRepository;
        this.taxiRouteMapperDtoToEntity = taxiRouteMapperDtoToEntity;
        this.routeMapperEntityToDto = routeMapperEntityToDto;
        this.routeRepository = routeRepository;

    }


    public TaxiRoutesResponse findAllRoutes(){
        List<TaxiRoute> routes = findAll();

        List<TaxiRouteDTO> dtoRoutes =
                routeMapperEntityToDto.toDto(routes);
        double total = util.getTotal(dtoRoutes);

        return new TaxiRoutesResponse(dtoRoutes,total,"R");
    }


    public FileResponse saveFromCSVFile(MultipartFile file){
        try{

            if (csvUtil.validateHeader(file)){
                throw new FileException("File " +
                        "columns incorrect.");
            }
        } catch (IOException e) {
            throw new FileException(
                    "Failed to process file.");
        }


        List<TaxiRouteCsvDto> routeList = csvUtil.csvToObject(file);
        List<TaxiRoute> routes = new ArrayList<>();


        for (TaxiRouteCsvDto dto: routeList){
            String pickUp = dto.getPickUpLocation();
            String dropOff = dto.getDropOffLocation();

            if(util
                .isFromLocationAndToLocationNotEqual(
                        pickUp,dropOff)){
                if(util.checkIfRouteAlreadyExists(
                        routeRepository,pickUp,dropOff)){
                    TaxiRoute taxiRoute = csvUtil
                            .prepareCsvDtoForSave(dto,
                                    rankRepository,
                                    signRepository,
                                    taxiRouteMapperDtoToEntity);

                    TaxiRoute bidirectionalRoute = util
                            .prepareForRouteBidirectionalRoute(taxiRoute);

                    routes.add(taxiRoute);
                    routes.add(bidirectionalRoute);
                }

            } else {
                throw new DuplicateResourceException("Pick up " +
                        "and Drop off location " +
                        "cannot refer to the same place.");
            }

        }

        createAll(routes);

        return new FileResponse("File received.");
    }


    public ByteArrayResource returnCsvTemplate(){
        return csvUtil.createCsvTemplate();
    }


    public TaxiRouteDTO updateRoute(Long id , TaxiRouteDTO dto){

        String pickUpLocation = dto.getPickUpLocation();
        String pickUpAddress = dto.getPickUpLocationAddress();

        String dropOffLocation = dto.getDropOffLocation();
        String dropOffAddress = dto.getDropOffLocationAddress();


        if(existsById(id)){
            Long pickUpRankId = util
                    .findTaxiRankId(rankRepository
                            ,pickUpLocation,pickUpAddress);

            Long dropOffRankId = util
                    .findTaxiRankId(rankRepository,
                    dropOffLocation,dropOffAddress);

            Long taxiSignId = util
                    .findTaxiSignId(signRepository,
                    dto.getRouteSignDescription());

            TaxiRoute route = taxiRouteMapperDtoToEntity
                    .toEntity(dto);

            route.setId(id);
            route.getFromLocationTaxiRank()
                    .setId(pickUpRankId);

            route.getToLocationTaxiRank()
                    .setId(dropOffRankId);

            route.getTaxiSign()
                    .setId(taxiSignId);

            update(route);

            return routeMapperEntityToDto.toDto(route);
        } else {
            throw new ResourceNotFoundException("Resource with" +
                    " specified id not found.");
        }

    }


    public void deleteRoute(Long id){
        deleteById(id);
    }


    public void saveRoute(TaxiRouteDTO dto){

        String pickUp = dto.getPickUpLocation();
        String dropOff = dto.getDropOffLocation();

        List<TaxiRoute> routes = new ArrayList<>();

        if(util.isFromLocationAndToLocationNotEqual(
                pickUp, dropOff)){

            if(util.checkIfRouteAlreadyExists(routeRepository,
                    pickUp,dropOff)){

                TaxiRoute route = util.prepareTaxiRouteDtoForSave(dto,
                        rankRepository,
                        signRepository,
                        taxiRouteMapperDtoToEntity);

                TaxiRoute bidirectionalRoute = util
                        .prepareForRouteBidirectionalRoute(route);

                routes.add(route);
                routes.add(bidirectionalRoute);

                createAll(routes);
            }
        } else {
            throw new DuplicateResourceException("Pick up and " +
                    "Drop off location " +
                    "cannot refer to the same place.");
        }

    }


    public void updateTaxiSign(Long id, TaxiSignDTO dto){
            taxiSignService.updateTaxiSign(id,dto);
    }


    public void saveTaxiRank(TaxiRankDTO dto){
        taxiRankService.saveTaxiRank(dto);
    }


    public TaxiSignResponse findAllTaxiSigns(){
        return taxiSignService.findAllTaxiSigns();
    }


    public void updateTaxiRank(Long id,TaxiRankDTO dto){
        taxiRankService.updateTaxiRank(id,dto);
    }


    public TaxiRankResponse findAllTaxiRanks(){
        return taxiRankService.returnAllTaxiSigns();
    }




    


}
