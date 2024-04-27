package com.edu.tool.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface CrudService<T, ID> {
    List<T> getAllItems(PageRequest pageRequest);

    T getById(ID id);

    void delete(ID id);

    void save(T item);

    T update(ID id, T item);
}
