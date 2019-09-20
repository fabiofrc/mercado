/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade;

import com.gdados.projeto.model.Grupo;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GrupoFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Grupo save(Grupo entity) {
        em.persist(entity);
        return entity;
    }

    public Grupo update(Grupo entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(Grupo entity) {
        em.remove(entity);
    }

    public List<Grupo> getAll() {
        Query q = em.createQuery("SELECT p FROM Grupo p");
        return q.getResultList();
    }

    public Grupo getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Grupo c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Grupo) q.getSingleResult();
    }

    public int count() {
        Query q = em.createQuery("select count(p) from Grupo p");
        int contador = (int) q.getSingleResult();
        return contador;
    }
}
