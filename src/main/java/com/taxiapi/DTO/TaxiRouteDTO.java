package com.taxiapi.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaxiRouteDTO {

    private Long id;

    @NotBlank(message = "Pick up location is required.")
    private String pickUpLocation;

    @NotBlank(message = "Drop off location is required.")
    private String dropOffLocation;

    @NotBlank(message = "Pick up location address is required.")
    private String pickUpLocationAddress;

    @NotBlank(message = "Drop off location address is required.")
    private String dropOffLocationAddress;

    @NotNull(message = "Trip fare is required.")
    private Double routeFare;

    @NotBlank(message = "Sign description is required.")
    private String routeSignDescription;

}
