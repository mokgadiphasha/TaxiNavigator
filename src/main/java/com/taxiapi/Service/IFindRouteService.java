package com.taxiapi.Service;

public interface IFindRouteService<T,M>{
    <K> T routeFinder (M fromLocation,M toLocation, K db);
}
