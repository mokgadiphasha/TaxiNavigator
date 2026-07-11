package com.taxiapi.tests;

import com.taxiapi.DTO.TaxiRouteDTO;
import com.taxiapi.Repository.TaxiRouteRepository;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

//@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class AdminTests {
    @Autowired
    private MockMvc mockMvc;

//    @ServiceConnection
//    @Container
//    static PostgreSQLContainer<?> postgres =
//            new PostgreSQLContainer<>("postgres,16");


    private String url = "/api/admin";

    @Autowired
    private TestUtil testUtil;

    private static ArrayList<TaxiRouteDTO> taxiRoutes = new ArrayList<>();
    private MvcResult mvcResult;


    @Test
    @Order(1)
    void shouldGetRoutes() throws Exception {

        mvcResult = testUtil.getResult(url);

        TaxiRoutesResponse response =
                testUtil.convertMVCResultToObject(mvcResult,
                TaxiRoutesResponse.class);

        assertEquals(3,response.getRoutes().size());

        for (int i = 0; i < 2; i++) {
            TaxiRouteDTO actual = response.getRoutes().get(i);
            TaxiRouteDTO expected = taxiRoutes.get(i);

            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getDropOffLocation(), actual.getDropOffLocation());
            assertEquals(expected.getPickUpLocation(), actual.getPickUpLocation());
            assertEquals(expected.getDropOffLocationAddress(), actual.getDropOffLocationAddress());
            assertEquals(expected.getPickUpLocationAddress(), actual.getPickUpLocationAddress());
            assertEquals(expected.getRouteFare(), actual.getRouteFare());

        }
    }


    @Test
    @Order(2)
    void shouldGetUpdatedRoute() throws Exception {
        TaxiRouteDTO expected = taxiRoutes.get(0);
        expected.setRouteFare(500.00);

        mvcResult = testUtil.putResult(url,expected,expected.getId());

        TaxiRouteDTO actual =
                testUtil.convertMVCResultToObject(mvcResult,
                        TaxiRouteDTO.class);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getRouteFare(), actual.getRouteFare());

    }


    @Test
    @Order(3)
    void shouldSaveRoute() throws Exception {
        TaxiRouteDTO expected = new  TaxiRouteDTO(null,"Sandton",
                "Boksburg","145 high low street Johannesburg",
                "111 Mara Louw Boksburg",100.0,
                "Hold up three fingers please");

        taxiRoutes.add(expected);

        testUtil.postResult(url,expected);

        mvcResult = testUtil.getResult(url);

        TaxiRouteDTO actual = testUtil
                .convertMVCResultToObject(mvcResult,TaxiRoutesResponse.class)
                .getRoutes().get(3);


        assertEquals(expected.getDropOffLocation(), actual.getDropOffLocation());
        assertEquals(expected.getPickUpLocation(), actual.getPickUpLocation());
        assertEquals(expected.getDropOffLocationAddress(), actual.getDropOffLocationAddress());
        assertEquals(expected.getPickUpLocationAddress(), actual.getPickUpLocationAddress());
        assertEquals(expected.getRouteFare(), actual.getRouteFare());
    }


    @Test
    @Order(4)
    void shouldUploadCsv() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "routes.csv",
                "text/csv",
                """
                pickUpLocation,pickUpLocationAddress,dropOffLocation,dropOffLocationAddress,routeFare,routeSignDescription
                                Greenstone Mall,"1 Greenstone Drive, Edenvale",Modderfontein,"45 Flamingo Road, Modderfontein",35.0,Point To The Sky
                                Carnival City,"1 Century Road, Brakpan",Dalpark,"22 Railway Street, Brakpan",30.0,Raise Your Palm
                                Kempton Park,"18 Pretoria Road, Kempton Park",Festival Mall,"C R Swart Drive, Kempton Park",20.0,Tap The Window Twice
        """.getBytes()
        );

        mockMvc.perform(multipart(url + "/template")
                        .file(file))
                .andExpect(status().isOk());

        mvcResult = testUtil.getResult(url);

        TaxiRoutesResponse actual = testUtil
                .convertMVCResultToObject(mvcResult,TaxiRoutesResponse.class);

        assertEquals(9,actual.getRoutes().size());
    }


    @Test
    void shouldReturnRoutesCsv() throws Exception {

        MvcResult result = mockMvc.perform(get(url + "/template"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andExpect(header().string(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=template.csv"))
                .andReturn();

        String csv = result.getResponse().getContentAsString();
        assertEquals("pickUpLocation,pickUpLocationAddress," +
                "dropOffLocation,dropOffLocationAddress,routeFare," +
                "routeSignDescription".trim(),csv.trim()                                                                               );

    }
    
    
    @BeforeEach
    void setup() {
        TaxiRouteDTO intem1 =  new TaxiRouteDTO(1L,"MTN Taxi Rank",
                "Flowerina Store","355 Plain Street Johannesburg",
                "111 Rosebank Johannesburg",150.0,
                "Hold up two fingers please");

        TaxiRouteDTO intem2 =  new TaxiRouteDTO(2L,"East Rand Mall",
                "West Central Road","780 Naledi street Boksburg",
                "245 West Central Road Dawn Park",50.0,
                "Hold up three fingers please");

        taxiRoutes.add(intem1);
        taxiRoutes.add(intem2);


    }
}
