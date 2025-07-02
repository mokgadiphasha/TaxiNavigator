package com.taxiapi.Service;

public interface IExistById<ID> {
    boolean existsById(ID id);
}
