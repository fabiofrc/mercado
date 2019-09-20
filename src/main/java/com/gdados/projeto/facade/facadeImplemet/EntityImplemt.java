/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade.facadeImplemet;

import java.util.List;

/**
 *
 * @author fabio
 * @param <T>
 */
public interface EntityImplemt<T> {

    public T save(T entity);

    public T update(T entity);

    public void delete(T entity);

    public List<T> getAll();

    public T getById(Long id);
}
