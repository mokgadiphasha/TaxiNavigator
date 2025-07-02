package com.taxiapi.RequestDTO;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaxiSignDTO {
    private Long id;
    private String signDescription;
}
