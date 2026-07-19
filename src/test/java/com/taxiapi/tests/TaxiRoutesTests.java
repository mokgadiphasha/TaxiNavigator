package com.taxiapi.tests;


import com.taxiapi.DTO.TaxiRankDTO;
import com.taxiapi.DTO.TaxiRouteDTO;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class TaxiRoutesTests {

    private String url = "/api/users/routes";

    @Autowired
    private TestUtil testUtil;
    @Autowired
    private MockMvc mockMvc;

    private ArrayList<TaxiRouteDTO> taxiRoute = new ArrayList<>();
    private MvcResult mvcResult;


    @Test
    void shouldGetRoute() throws Exception {

        TaxiRouteDTO expected =  new TaxiRouteDTO(1L,"MTN Taxi Rank",
                "Flowerina Store","355 Plain Street Johannesburg",
                "111 Rosebank Johannesburg",150.0,
                "Hold up two fingers please");

        mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .param("from",expected.getPickUpLocation())
                        .param("to",expected.getDropOffLocation()))
                .andExpect(status().isOk())
                .andReturn();

        TaxiRouteDTO actual= testUtil
                .convertMVCResultToObject(mvcResult,
                        TaxiRoutesResponse.class).getRoutes().get(0);

        assertEquals(expected.getDropOffLocation(), actual.getDropOffLocation());
        assertEquals(expected.getPickUpLocation(), actual.getPickUpLocation());
        assertEquals(expected.getDropOffLocationAddress(), actual.getDropOffLocationAddress());
        assertEquals(expected.getPickUpLocationAddress(), actual.getPickUpLocationAddress());
        assertEquals(expected.getRouteFare(), actual.getRouteFare());





    }
}
