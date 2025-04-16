package com.taxiapi.Model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "taxi_signs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TaxiSign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByName
    @Column(nullable = false)
    private String signDescription;
}
