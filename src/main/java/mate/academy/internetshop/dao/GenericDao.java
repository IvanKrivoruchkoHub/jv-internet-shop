package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exceptions.DataProcessingExeption;

public interface GenericDao<T, I> {
    T create(T entity) throws DataProcessingExeption;

    Optional<T> get(I entityId) throws DataProcessingExeption;

    T update(T entity) throws DataProcessingExeption;

    boolean deleteById(I entityId) throws DataProcessingExeption;

    boolean delete(T entity) throws DataProcessingExeption;

    List<T> getAll() throws DataProcessingExeption;
}
