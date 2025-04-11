package com.taxiapi.Service;

public interface IFindRouteService<T,M,L>{
    <K> L routeFinder (M fromLocation,M toLocation, K db);
}
