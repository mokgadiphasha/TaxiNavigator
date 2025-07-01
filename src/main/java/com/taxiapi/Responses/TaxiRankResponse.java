package com.taxiapi.Responses;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.RequestDTO.TaxiRankDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TaxiRankResponse {
    private List<TaxiRankDTO> ranks;
}
