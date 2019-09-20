package com.gdados.projeto.facade;

import com.gdados.projeto.model.SubCategoria;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SubCategoriaFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public SubCategoria save(SubCategoria entity) {
        em.persist(entity);
        return entity;
    }

    public SubCategoria update(SubCategoria entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(SubCategoria entity) {
        em.remove(entity);
    }

    public List<SubCategoria> getAll() {
        Query q = em.createQuery("SELECT p FROM SubCategoria p");
        return q.getResultList();
    }

    public SubCategoria getById(Long id) {
        Query q = em.createQuery("SELECT c FROM SubCategoria c WHERE c.id = :id");
        q.setParameter("id", id);
        return (SubCategoria) q.getSingleResult();
    }

    public List<SubCategoria> listaSubCategoriaByCategoria(Long id) {
        try {
            Query q = em.createQuery("SELECT s FROM SubCategoria s JOIN s.categoria c WHERE c.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public int count() {
        Query q = em.createQuery("select count(p) from SubCategoria p");
        int contador = (int) q.getSingleResult();
        return contador;
    }
}
