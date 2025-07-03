package com.taxiapi.Model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "taxi_routes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    @JoinColumn(name = "from_location_rank_id", nullable = false)
    private TaxiRank fromLocationTaxiRank;

    @ManyToOne
    @JoinColumn(name = "to_location_rank_id", nullable = false)
    private TaxiRank toLocationTaxiRank;
}
