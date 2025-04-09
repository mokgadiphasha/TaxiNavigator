package com.taxiapi.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class GenericCrudService<T,ID> implements ICreateService<T>, IDeleteService<ID>, IFindAllService<T>, IUpdateService<T>, IFindById<T,ID> {

    private final JpaRepository<T, ID> repository;

    public GenericCrudService(JpaRepository<T, ID> repository){
        this.repository = repository;
    }

    @Override
    public void create(T entity) {
        repository.save(entity);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);

    }

    @Override
    public List<T> findAll() {
       return repository.findAll();
    }

    @Override
    public void update(T entity) {
        repository.save(entity);
    }


    @Override
    public T findById(ID id) {
        return repository.findById(id).get();
    }
}
