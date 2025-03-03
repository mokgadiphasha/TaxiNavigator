package com.taxiapi;

import org.springframework.boot.SpringApplication;

public class TestTaxiNavigatorApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(TaxiNavigatorApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
