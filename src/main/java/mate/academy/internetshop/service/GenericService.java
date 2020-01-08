package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, I> {
    T create(T entity);

    Optional<T> get(I entityId);

    T update(T entity);

    boolean deleteById(I entityId);

    boolean delete(T entity);

    List<T> getAll();
}
