package com.taxiapi.Model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "taxi_signs",
uniqueConstraints =
@UniqueConstraint(columnNames =
        {"signDescription"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaxiSign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String signDescription;

    public TaxiSign(String signDescription){
        this.signDescription = signDescription;
    }
}
