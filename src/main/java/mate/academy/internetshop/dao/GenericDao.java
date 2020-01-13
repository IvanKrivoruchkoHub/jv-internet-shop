package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, I> {
    T create(T entity);

    Optional<T> get(I entityId);

    T update(T entity);

    boolean deleteById(I entityId);

    boolean delete(T entity);

    List<T> getAll();
}
