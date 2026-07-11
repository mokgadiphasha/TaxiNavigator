package com.taxiapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class TestUtil {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    public <T> T convertMVCResultToObject(MvcResult mvcResult,
                                          Class<T> clazz){

        try{
            String jsonResponse = mvcResult.getResponse()
                    .getContentAsString();
            return objectMapper.readValue(jsonResponse,
                    clazz);

        } catch (IOException  e) {
            throw new RuntimeException(e);
        }

    }


    public <T> String convertToJSON(T objectToSerialize){

        try{
            return objectMapper.writeValueAsString(objectToSerialize);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public MvcResult getResult(String url) throws Exception {

        return mockMvc.perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(status().isOk())
                .andReturn();

    }


    public <T> MvcResult postResult(String url, T object) throws Exception {
        return mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJSON(object)))
                .andExpect(status().isCreated())
                .andReturn();
    }


    public <T> MvcResult putResult(String url, T object,Long id) throws Exception {
        return mockMvc.perform(put(url + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJSON(object)))
                .andExpect(status().isOk())
                .andReturn();
    }



}
