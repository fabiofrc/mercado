package com.gdados.projeto.facade;

import com.gdados.projeto.model.Promocao;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PromocaoFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Promocao save(Promocao entity) {
        em.persist(entity);
        return entity;
    }

    public Promocao update(Promocao entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(Promocao entity) {
        em.remove(entity);
    }

    public List<Promocao> getAll() {
        Query q = em.createQuery("SELECT p FROM Promocao p");
        return q.getResultList();
    }

    public Promocao getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Promocao c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Promocao) q.getSingleResult();
    }

    public List<Promocao> listaPromocaoAtivaByData() {
        try {
            Query q = em.createQuery("SELECT p FROM Promocao p WHERE p.dataEncerramento >= :dataAtual ORDER BY p.dataEncerramento DESC");
            q.setParameter("dataAtual", LocalDate.now());
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public int count() {
        Query q = em.createQuery("select count(p) from Promocao p");
        int contador = (int) q.getSingleResult();
        return contador;
    }
}
