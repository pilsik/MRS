package by.sivko.miningrigservice.dao;

import java.io.Serializable;

public interface GenericDao<T,PK extends Serializable> {
    void save(T t);
    T findOne(PK id);
    void remove(T t);
}
