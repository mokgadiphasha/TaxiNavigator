package com.taxiapi.Service;

public interface RouteFinder<T>{
    T routeFinder(T fromLocation,T toLocation);
}
