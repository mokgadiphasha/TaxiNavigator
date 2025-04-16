package com.taxiapi.Model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "taxi_routes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TaxiRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByName
    @Column(nullable = false)
    private String fromLocation;

    @CsvBindByName
    @Column(nullable = false)
    private String toLocation;

    @CsvBindByName
    @Column(nullable = false)
    private Double fare;

    @CsvRecurse
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "taxi_sign_id", nullable = false)
    private TaxiRank taxiRank;

    @CsvRecurse
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "rank_id", nullable = false)
    private TaxiSign taxiSign;
}
