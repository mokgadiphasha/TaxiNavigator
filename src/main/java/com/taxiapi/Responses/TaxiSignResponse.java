package com.taxiapi.Responses;

import com.taxiapi.DTO.TaxiSignDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TaxiSignResponse {
    private List<TaxiSignDTO> signs;
}
