package Eprodavnica.EprodavnicaBackend.service;

import java.util.List;

public interface ServiceInterface<T>{
    List<T> findAll();

    T findOne(Integer id);

    T create(T entity);

    T update(T entity, Integer id);

    void delete(Integer id);
}
