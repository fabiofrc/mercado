package com.gdados.projeto.facade;

import com.gdados.projeto.model.Endereco;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class EnderecoFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Endereco save(Endereco entity) {
        em.persist(entity);
        return entity;
    }

    public Endereco update(Endereco entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(Endereco entity) {
        em.remove(entity);
    }

    public List<Endereco> getAll() {
        Query q = em.createQuery("SELECT p FROM Endereco p");
        return q.getResultList();
    }

    public Endereco getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Endereco c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Endereco) q.getSingleResult();
    }

    public int count() {
        Query q = em.createQuery("select count(p) from Endereco p");
        int contador = (int) q.getSingleResult();
        return contador;
    }

}
