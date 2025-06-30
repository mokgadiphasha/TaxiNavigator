package com.taxiapi.Model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "taxi_ranks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class TaxiRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false)
    private String locationAddress;

    public TaxiRank(String locationName,String locationAddress){
        this.locationName = locationName;
        this.locationAddress = locationAddress;
    }


}
