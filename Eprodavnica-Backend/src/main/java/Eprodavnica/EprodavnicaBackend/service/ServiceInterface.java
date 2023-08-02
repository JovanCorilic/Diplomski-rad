package Eprodavnica.EprodavnicaBackend.service;

import java.util.List;

public interface ServiceInterface<T>{
    List<T> findAll();

    T findOne(String id);

    T create(T entity);

    T update(T entity, String id);

    void delete(String id);
}
