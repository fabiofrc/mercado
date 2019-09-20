package com.gdados.projeto.facade;

import com.gdados.projeto.model.Loja;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LojaFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Loja save(Loja entity) {
        em.persist(entity);
        return entity;
    }

    public Loja update(Loja entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(Loja entity) {
        em.remove(entity);
    }

    public List<Loja> getAll() {
        Query q = em.createQuery("SELECT p FROM Loja p");
        return q.getResultList();
    }

    public Loja getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Loja c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Loja) q.getSingleResult();
    }

    public Loja buscaPessoaJuridicaByIdUsuario(Long id) {
        try {
            Query q = em.createQuery("SELECT p FROM Loja p JOIN p.usuario u WHERE u.id = :id");
            q.setParameter("id", id);
            return (Loja) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public int count() {
        Query q = em.createQuery("select count(p) from Loja p");
        int contador = (int) q.getSingleResult();
        return contador;
    }
}
