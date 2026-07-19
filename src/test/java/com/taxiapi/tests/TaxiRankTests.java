package com.taxiapi.tests;

import com.taxiapi.DTO.TaxiRankDTO;
import com.taxiapi.DTO.TaxiRouteDTO;
import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Responses.TaxiRankResponse;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class TaxiRankTests {

    private String url = "/api/admin/taxirank";

    @Autowired
    private TestUtil testUtil;

    private  ArrayList<TaxiRankDTO> taxiRank = new ArrayList<>();
    private MvcResult mvcResult;

    @Test
    void shouldGetAllRanks() throws Exception {

        mvcResult = testUtil.getResult(url);

        TaxiRankResponse response =
                testUtil.convertMVCResultToObject(mvcResult,
                        TaxiRankResponse.class);

        assertEquals(6,response.getRanks().size());


        for (int i = 0; i < 2; i++) {
            TaxiRankDTO actual = response.getRanks().get(i);
            TaxiRankDTO expected = taxiRank.get(i);

//            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getLocationName(), actual.getLocationName());
            assertEquals(expected.getLocationAddress(), actual.getLocationAddress());

        }
    }


    @Test
    void shouldUpdateTaxiRank() throws Exception {
            TaxiRankDTO expected = taxiRank.get(0);
            expected.setLocationName("Rosalia Corner");
        testUtil.putResult(url,expected,1L);

        mvcResult = testUtil.getResult(url);
        TaxiRankDTO actual =
                testUtil.convertMVCResultToObject(mvcResult,
                        TaxiRankResponse.class).getRanks().get(0);

        assertEquals(expected.getLocationAddress(), actual.getLocationAddress());
        assertEquals(expected.getLocationName(), actual.getLocationName());

    }




    @BeforeEach
    void setup() throws Exception {
        TaxiRankDTO item1 = new TaxiRankDTO(1L, "Sesame Corner",
                "145 Trent street Johannesburg");
        TaxiRankDTO item2 = new TaxiRankDTO(2L, "Flowerina Store",
                "111 Rosebank Johannesburg");

        taxiRank.add(item1);
        taxiRank.add(item2);




    }
}
