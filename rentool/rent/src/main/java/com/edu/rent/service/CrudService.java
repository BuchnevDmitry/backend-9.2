package com.edu.rent.service;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> getAllItems();

    T getById(ID id);

    void delete(ID id);

    void save(T item);

    T update(ID id, T item);
}
