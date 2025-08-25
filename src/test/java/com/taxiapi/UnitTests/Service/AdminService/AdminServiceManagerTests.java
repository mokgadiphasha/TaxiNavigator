package com.taxiapi.UnitTests.Service.AdminService;

import com.taxiapi.DTO.TaxiRankDTO;
import com.taxiapi.DTO.TaxiRouteDTO;
import com.taxiapi.Mapper.TaxiRouteMapperDtoToEntity;
import com.taxiapi.Mapper.TaxiRouteMapperEntityToDto;
import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Repository.TaxiRankRepository;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Repository.TaxiSignRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.AdminService.AdminServiceManager;
import com.taxiapi.Service.AdminService.AdminTaxiRankService;
import com.taxiapi.Service.AdminService.AdminTaxiSignService;
import com.taxiapi.TestConfig;
import com.taxiapi.Utility.CSVUtilityService;
import com.taxiapi.Utility.RouteUtilityService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Import(TestConfig.class)
public class AdminServiceManagerTests {


    TaxiRouteRepository routeRepository  = mock(TaxiRouteRepository.class);

    @Mock
    TaxiRankRepository rankRepository;

    @Mock
    TaxiSignRepository signRepository;

    @Mock
    TaxiRouteMapperEntityToDto entityToDto;

    @Mock
    TaxiRouteMapperDtoToEntity dtoToEntity;

    @Autowired
    @InjectMocks
    AdminServiceManager serviceManager;


    private static List<TaxiRoute> taxiRouteList = new ArrayList<>();
    private static List<TaxiRouteDTO> taxiRouteListDTO = new ArrayList<>();


    @Disabled
    public void testIfFindsAllRoutes(){

        TaxiRoutesResponse expectedResult = new TaxiRoutesResponse(
                taxiRouteListDTO,160.0,"R");

        when(routeRepository.findAll())
                .thenReturn(taxiRouteList);

        when(entityToDto.toDto(taxiRouteList))
                .thenReturn(taxiRouteListDTO);

        TaxiRoutesResponse result = serviceManager.findAllRoutes();

        assertEquals(expectedResult.getRoutes().size(),
                result.getRoutes().size());

        assertEquals(expectedResult.getTotalTaxiFare(),
                result.getTotalTaxiFare());

        assertEquals(expectedResult.getCurrency(),
                result.getCurrency());
    }



    @Disabled
    public void testUpdateRoute(){

        TaxiRoute route = taxiRouteList.get(0);
        TaxiRouteDTO  expected = taxiRouteListDTO.get(0);
        expected.setRouteFare(100.0);

        TaxiRank mock1 = route.getToLocationTaxiRank();

        TaxiRank mock2 = route.getFromLocationTaxiRank();

        TaxiSign mock3 = route.getTaxiSign();

        when(routeRepository.existsById(1L))
                .thenReturn(true);

        when(rankRepository
                .findByLocationNameAndLocationAddress(
                "Sesame Corner",
                        "111 Rosebank Johannesburg"))
                .thenReturn(mock1);

        when(rankRepository
                .findByLocationNameAndLocationAddress(
                "Flowerina Store",
                "145 Trent street Johannesburg"))
                .thenReturn(mock2);

        when(signRepository
                .findBySignDescription("Hold Four Fingers Up"))
                .thenReturn(mock3);

        when(dtoToEntity.toEntity(expected))
                .thenReturn(route);

        when(entityToDto.toDto(route))
                .thenReturn(expected);

        TaxiRouteDTO result = serviceManager
                .updateRoute(1L,expected);

        assertEquals(expected.getRouteFare(),
                result.getRouteFare());

        assertEquals(expected.getId(),
                result.getId());

        assertEquals(expected.getDropOffLocation(),
                result.getDropOffLocation());

        assertEquals(expected.getDropOffLocationAddress(),
                result.getDropOffLocationAddress());

        assertEquals(expected.getPickUpLocation(),
                result.getPickUpLocation());

        assertEquals(expected.getPickUpLocationAddress(),
                result.getPickUpLocationAddress());

        assertEquals(expected.getRouteSignDescription(),
                result.getRouteSignDescription());
    }


    @Test
    public void testDeleteRoute(){

        serviceManager.deleteRoute(1L);
       verify(routeRepository,times(1))
               .deleteById(1L);
    }

    @Disabled
    public void testSaveOnNonExistentRoute(){

        TaxiRouteDTO routeDTO = new TaxiRouteDTO(null,"MTN Taxi Rank",
                "Springs",
                "165 Trends street Johannesburg",
                "121 Springs Johannesburg",
                80.0,"Hold Five Fingers Up");

        TaxiRank from1 =  new TaxiRank(null,"MTN Taxi Rank",
                "165 Trends street Johannesburg");

        TaxiRank to1 = new TaxiRank("Springs",
                "121 Springs Johannesburg");

        TaxiSign sign = new TaxiSign(null,"Hold Five Fingers Up");

        TaxiRoute route1 = new TaxiRoute(null,"MTN Taxi Rank",
                "Springs",100.0,
                sign,from1,to1);

        TaxiRoute route2 = new TaxiRoute(null,"Springs",
                "MTN Taxi Rank",100.0,
                sign,to1,from1);

        when(routeRepository
                .existsByFromLocationAndToLocation("MTN Taxi Rank",
                "Springs"))
                .thenReturn(false);

        when(dtoToEntity.toEntity(routeDTO))
                .thenReturn(route1);

        when(signRepository
                .existsBySignDescription("Hold Five Fingers Up"))
                .thenReturn(false);

        when(signRepository
                .findBySignDescription(sign
                        .getSignDescription()))
                .thenReturn(sign);

        when(rankRepository
                .existsByLocationNameAndLocationAddress(
                        from1.getLocationName(),
                        from1.getLocationAddress()))
                .thenReturn(false);

        when(rankRepository
                .findByLocationNameAndLocationAddress(
                        from1.getLocationName(),
                from1.getLocationAddress()))
                .thenReturn(from1);

        when(rankRepository
                .existsByLocationNameAndLocationAddress(
                        to1.getLocationName(),
                        to1.getLocationAddress()))
                .thenReturn(false);

        when(rankRepository
                .findByLocationNameAndLocationAddress(
                        to1.getLocationName(),
                        to1.getLocationAddress()))
                .thenReturn(to1);

        List<TaxiRoute>  routes = new ArrayList<>();
        routes.add(route1);
        routes.add(route2);

        serviceManager.saveRoute(routeDTO);

        verify(routeRepository,times(1))
                .saveAll(anyList());
        verify(signRepository,times(1))
                .save(any(TaxiSign.class));
        verify(rankRepository,times(2))
                .save(any(TaxiRank.class));

    }





    @BeforeEach
    void initialize (){

        TaxiRank from1 = new TaxiRank(1L,"Sesame Corner",
                "111 Rosebank Johannesburg");

        TaxiRank to1 = new TaxiRank(2L,"Flowerina Store",
                "145 Trent street Johannesburg");

        TaxiSign sign = new TaxiSign(1L,"Hold Four Fingers Up");

        TaxiRoute route1 = new TaxiRoute(1L,"Sesame Corner",
                "Flowerina Store", 80.0,sign,from1,to1);


        TaxiRank to2 = new TaxiRank(1L,"Sesame Corner",
                "111 Rosebank Johannesburg");

        TaxiRank from2 = new TaxiRank(2L,"Flowerina Store",
                "145 Trent street Johannesburg");

        TaxiRoute route2 = new TaxiRoute(2L,"Flowerina Store",
                "Sesame Corner", 80.0,sign,from2,to2);

        taxiRouteList.add(route1);
        taxiRouteList.add(route2);

        TaxiRouteDTO routeDTO1 = new TaxiRouteDTO(1L,"Sesame Corner",
                "Flowerina Store",
                "111 Rosebank Johannesburg",
                "145 Trent street Johannesburg",
                80.0,"Hold Four Fingers Up");

        TaxiRouteDTO routeDTO2 = new TaxiRouteDTO(2L,"Flowerina Store",
                "Sesame Corner",
                "145 Trent street Johannesburg",
                "111 Rosebank Johannesburg",
                80.0,"Hold Four Fingers Up");

        taxiRouteListDTO.add(routeDTO1);
        taxiRouteListDTO.add(routeDTO2);
    }


    @AfterEach
    void intializeAfter(){
        taxiRouteList.clear();
        taxiRouteListDTO.clear();
    }
}
