package com.taxiapi;

import com.taxiapi.Utility.CSVUtilityService;
import com.taxiapi.Utility.RouteUtilityService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    RouteUtilityService routeUtilityService() {
        return new RouteUtilityService();
    }
    @Bean
    CSVUtilityService csvUtilityService() {
        return new CSVUtilityService();
    }
}

