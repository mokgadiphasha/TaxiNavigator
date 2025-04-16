package com.taxiapi.Service;

import java.util.List;

public interface ICreateService<T>{

    void create(T entity);

    void createAll(List<T> entities);
}
