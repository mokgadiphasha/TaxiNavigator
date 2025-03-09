package com.taxiapi.Repository;

import com.taxiapi.Model.TaxiRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiRouteRepository extends JpaRepository<TaxiRoute,Long> {
}
