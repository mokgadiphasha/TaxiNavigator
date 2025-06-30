package com.taxiapi.Repository;

import com.taxiapi.Model.TaxiSign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiSignRepository extends JpaRepository<TaxiSign,Long> {

    TaxiSign findBySignDescription(String sign);
}
