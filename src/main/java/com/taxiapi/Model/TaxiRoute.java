package com.taxiapi.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Taxi_routes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TaxiRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fromLocation;

    @Column(nullable = false)
    private String toLocation;

    @Column(nullable = false)
    private Double fare;

    @ManyToOne
    @JoinColumn(name = "taxi_sign_id", nullable = false)
    private TaxiSign taxiSign;

    @ManyToOne
    @JoinColumn(name = "rank_id", nullable = false)
    private TaxiRank taxiRank;
}
