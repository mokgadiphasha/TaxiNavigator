package com.taxiapi.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaxiRankDTO {
    private Long id;

    @NotBlank(message = "Location name is required.")
    private String locationName;

    @NotBlank(message = "Location address is required.")
    private String locationAddress;
}
