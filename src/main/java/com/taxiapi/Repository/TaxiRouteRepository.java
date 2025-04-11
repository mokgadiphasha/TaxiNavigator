package com.taxiapi.Repository;

import com.taxiapi.Model.TaxiRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxiRouteRepository extends JpaRepository<TaxiRoute,Long> {
    List<TaxiRoute> findByfromLocationAndtoLocation(String fromLocation,
                                                    String toLocation);
}
