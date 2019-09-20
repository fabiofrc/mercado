/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade;

import com.gdados.projeto.facade.facadeImplemet.EntityImplemt;
import com.gdados.projeto.model.Categoria;
import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author fabio
 */
public class TesteFacade implements Serializable, EntityImplemt<Categoria> {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    @Override
    public Categoria save(Categoria entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public Categoria update(Categoria entity) {
        em.merge(entity);
        return entity;
    }

    @Override
    public void delete(Categoria entity) {
        em.remove(entity);
    }

    @Override
    public List<Categoria> getAll() {
        Query q = em.createQuery("SELECT p FROM Categoria p");
        return q.getResultList();
    }


    @Override
    public Categoria getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Categoria c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Categoria) q.getSingleResult();
    }

}
