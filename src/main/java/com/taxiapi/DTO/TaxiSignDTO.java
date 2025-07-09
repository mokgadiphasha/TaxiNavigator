package com.taxiapi.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaxiSignDTO {
    private Long id;

    @NotBlank(message = "Sign description is required.")
    private String signDescription;
}
