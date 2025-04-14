package com.taxiapi.Repository;

import com.taxiapi.Model.TaxiRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxiRouteRepository extends JpaRepository<TaxiRoute,Long> {
    List<TaxiRoute> findByFromLocationAndToLocation(String fromLocation,
                                                    String toLocation);

    List<TaxiRoute> findByFromLocation(String fromLocation);
}
