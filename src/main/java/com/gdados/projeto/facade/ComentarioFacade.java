package com.gdados.projeto.facade;

import com.gdados.projeto.model.Comentario;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ComentarioFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Comentario save(Comentario entity) {
        em.persist(entity);
        return entity;
    }

    public Comentario update(Comentario entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(Comentario entity) {
        em.remove(entity);
    }

    public List<Comentario> getAll() {
        Query q = em.createQuery("SELECT p FROM Comentario p");
        return q.getResultList();
    }

    public Comentario getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Comentario c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Comentario) q.getSingleResult();
    }

    public List<Comentario> listaComentarioByNoticia(Long id) {
        try {
            Query q = em.createQuery("SELECT c FROM Comentario c JOIN c.produto n WHERE n.id = :id ORDER BY c.id DESC");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<Comentario> listaComentarioByUsuario(Long id) {
        try {
            Query q = em.createQuery("SELECT c FROM Comentario c JOIN c.usuario u WHERE u.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public Long contaComentarioByProduto(Long id) {
        Query q = em.createQuery("select count(c) FROM Comentario c JOIN c.produto n WHERE n.id = :id");
        q.setParameter("id", id);
        Long contador = (Long) q.getSingleResult();
        return contador;
    }

    public Long somaComentarioByProduto(Long id) {
        try {
            @SuppressWarnings("JPQLValidation")
            Query q = em.createQuery("select SUM(c.avaliacao) FROM Comentario c JOIN c.produto n WHERE n.id = :id");
            q.setParameter("id", id);
            Long contador = (Long) q.getSingleResult();
            return contador;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public double mediaComentarioByProduto(Long id) {
        try {
            @SuppressWarnings("JPQLValidation")
            Query q = em.createQuery("select AVG(c.avaliacao) FROM Comentario c JOIN c.produto n WHERE n.id = :id");
            q.setParameter("id", id);
            double contador = (double) q.getSingleResult();
            return contador;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return 0;
    }

    public Long contaComentarioByData(LocalDate dataRegistro) {
        try {
            Query q = em.createQuery("select count(c) FROM Comentario c WHERE c.dataRegistro = :dataRegistro");
            q.setParameter("dataRegistro", dataRegistro);
            Long contador = (Long) q.getSingleResult();
            return contador;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public Long contaTotal() {
        Query q = em.createQuery("select count(c) FROM Comentario c");
        Long contador = (Long) q.getSingleResult();
        return contador;
    }

    public int count() {
        Query q = em.createQuery("select count(p) from Comentario p");
        int contador = (int) q.getSingleResult();
        return contador;
    }
}
