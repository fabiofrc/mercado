package com.gdados.projeto.facade;

import com.gdados.projeto.model.Arquivo;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ArquivoFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Arquivo save(Arquivo entity) {
        em.persist(entity);
        return entity;
    }

    public Arquivo update(Arquivo entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(Arquivo entity) {
        em.remove(entity);
    }

    public List<Arquivo> getAll() {
        Query q = em.createQuery("SELECT p FROM Arquivo p");
        return q.getResultList();
    }

    public Arquivo getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Arquivo c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Arquivo) q.getSingleResult();
    }

    public Arquivo arquivoById(Long id) {
        Query q = em.createQuery("select a from Arquivo a WHERE a.id  = :id");
        q.setParameter("id", id);
        return (Arquivo) q.getSingleResult();
    }

    public List<Arquivo> arquivoByProtudo(Long id) {
        Query q = em.createQuery("select a from Arquivo a JOIN a.produto p WHERE p.id  = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }

}
