package com.taxiapi.Model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "taxi_ranks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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
