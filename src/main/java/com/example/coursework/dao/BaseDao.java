package com.example.coursework.dao;

import java.util.List;

public interface BaseDao<T> {
    T findById(int id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}