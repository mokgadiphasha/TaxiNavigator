package com.taxiapi.Service;

public interface RouteFinder<T,M>{
    T routeFinder(M fromLocation,M toLocation);
}
