package com.taxiapi.Repository;

import com.taxiapi.Model.TaxiRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiRankRepository extends JpaRepository<TaxiRank,Long> {
}
