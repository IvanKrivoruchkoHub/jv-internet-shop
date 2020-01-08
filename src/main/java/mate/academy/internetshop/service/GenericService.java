package mate.academy.internetshop.service;

import java.util.List;

public interface GenericService<T, I> {
    T create(T entity);

    T get(I entityId);

    T update(T entity);

    boolean deleteById(I entityId);

    boolean delete(T entity);

    List<T> getAll();
}
