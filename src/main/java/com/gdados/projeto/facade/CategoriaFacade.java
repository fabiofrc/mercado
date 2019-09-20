package com.gdados.projeto.facade;

import com.gdados.projeto.model.Categoria;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CategoriaFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Categoria save(Categoria entity) {
        em.persist(entity);
        return entity;
    }

    public Categoria update(Categoria entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(Categoria entity) {
        em.remove(entity);
    }

    public List<Categoria> getAll() {
        Query q = em.createQuery("SELECT p FROM Categoria p");
        return q.getResultList();
    }

    public Categoria getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Categoria c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Categoria) q.getSingleResult();
    }

    public int count() {
        Query q = em.createQuery("select count(p) from Categoria p");
        int contador = (int) q.getSingleResult();
        return contador;
    }
}
