package com.taxiapi.RequestDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaxiRankDTO {
    private Long id;
    private String locationName;
    private String locationAddress;
}
