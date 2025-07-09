package com.taxiapi.Utility;

import com.taxiapi.Mapper.TaxiRouteMapperDtoToEntity;
import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Repository.TaxiRankRepository;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Repository.TaxiSignRepository;
import com.taxiapi.DTO.TaxiRouteDTO;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RouteUtilityService {


    public double getTotal(List<TaxiRouteDTO> routes){
        return routes.stream()
                .mapToDouble(TaxiRouteDTO::getRouteFare)
                .sum();
    }


    public boolean isFromLocationAndToLocationNotEqual(
                                              String pickUp, String dropOff){

        return !pickUp.equalsIgnoreCase(dropOff);

    }


    public Map<String, Long> validateIfEntitiesInDatabase(
            TaxiRankRepository rankRepository,
            TaxiSignRepository  signRepository,
            TaxiRoute route){

        Map<String,Long> result = new HashMap<>();

        Long signId = validateSignEntityForSave(signRepository,
                route);

        Long fromTaxiRankId = validateRankEntityForSaving(
                rankRepository,route
                        .getFromLocationTaxiRank());

        Long toTaxiRankId = validateRankEntityForSaving(
                rankRepository,route
                        .getToLocationTaxiRank());

        result.put("taxiSignId",signId);
        result.put("fromTaxiRankId", fromTaxiRankId);
        result.put("toTaxiRankId", toTaxiRankId);

        return result;

    }


    public Long validateRankEntityForSaving(TaxiRankRepository
                              rankRepository,
                            TaxiRank rank){

        String locationName = rank.getLocationName();
        String locationAddress = rank.getLocationAddress();

        boolean result = rankRepository
                .existsByLocationNameAndLocationAddress(locationName,
                        locationAddress);

        if(!result)
            rankRepository
                    .save(new TaxiRank(locationName,
                            locationAddress));
        return rankRepository
                .findByLocationNameAndLocationAddress(
                        locationName,locationAddress)
                .getId();

    }


    public Long validateSignEntityForSave(TaxiSignRepository
                              signRepository,
                              TaxiRoute route){

        String signDescription = route.getTaxiSign()
                .getSignDescription();

        boolean result = signRepository
                .existsBySignDescription(signDescription);

        if(!result) signRepository
                .save(new TaxiSign(signDescription));

        return signRepository
                .findBySignDescription(
                        signDescription)
                .getId();
    }


    public Long findTaxiRankId(TaxiRankRepository repository,
                               String locationName, String address){

        TaxiRank rank =  repository
                .findByLocationNameAndLocationAddress(
                        locationName,address);
        return rank.getId();

    }


    public Long findTaxiSignId(TaxiSignRepository repository,
                               String signDescription){

        TaxiSign taxiSign = repository
                .findBySignDescription(signDescription);

        return taxiSign.getId();
    }


    public TaxiRoute prepareTaxiRouteDtoForSave(TaxiRouteDTO dto,
                TaxiRankRepository rankRepository,
                TaxiSignRepository signRepository,
                TaxiRouteMapperDtoToEntity mapper){

        TaxiRoute route = mapper
                .toEntity(dto);

        Map<String,Long> result =
                validateIfEntitiesInDatabase(rankRepository,
                        signRepository,route);

        route.getFromLocationTaxiRank()
                .setId(result
                        .get("fromTaxiRankId"));

        route.getToLocationTaxiRank()
                .setId(result
                        .get("toTaxiRankId"));

        route.getTaxiSign()
                .setId(result
                        .get("taxiSignId"));
        return route;
    }


//TODO: Call this somewhere in admin service before saving
    public boolean checkIfRouteAlreadyExists(
            TaxiRouteRepository repository,
            String fromLocation,String toLocation){

        return !repository
                .existsByFromLocationAndToLocation(
                        fromLocation,toLocation);
    }


    public TaxiRoute prepareForRouteBidirectionalRoute(TaxiRoute route){

        TaxiRoute bidirectionalRoute = new TaxiRoute();

        bidirectionalRoute.setFromLocation(route.getToLocation());
        bidirectionalRoute.setFare(route.getFare());
        bidirectionalRoute.setToLocation(route.getFromLocation());
        bidirectionalRoute.setTaxiSign(route.getTaxiSign());

        bidirectionalRoute
                .setFromLocationTaxiRank(
                        route.getToLocationTaxiRank());

        bidirectionalRoute
                .setToLocationTaxiRank(
                        route.getFromLocationTaxiRank());

        return bidirectionalRoute;
    }
}
