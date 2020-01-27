package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.exceptions.DataProcessingExeption;

public interface GenericService<T, I> {
    T create(T entity) throws DataProcessingExeption;

    T get(I entityId) throws DataProcessingExeption;

    T update(T entity) throws DataProcessingExeption;

    boolean deleteById(I entityId) throws DataProcessingExeption;

    boolean delete(T entity) throws DataProcessingExeption;

    List<T> getAll() throws DataProcessingExeption;
}
