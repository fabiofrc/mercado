package com.gdados.projeto.facade;

import com.gdados.projeto.model.Pedido;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PedidoFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Pedido save(Pedido entity) {
        em.persist(entity);
        return entity;
    }

    public Pedido update(Pedido entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(Pedido entity) {
        em.remove(entity);
    }

    public List<Pedido> getAll() {
        Query q = em.createQuery("SELECT p FROM Pedido p");
        return q.getResultList();
    }

    public Pedido getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Pedido c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Pedido) q.getSingleResult();
    }

    public Pedido porId(Long id) {
        return em.find(Pedido.class, id);
    }

    public List<Pedido> listaPedidoByUsuario(Long id) {
        try {
            @SuppressWarnings("JPQLValidation")
            Query q = em.createQuery("SELECT n FROM Pedido n JOIN n.usuario u WHERE u.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public int count() {
        Query q = em.createQuery("select count(p) from Pedido p");
        int contador = (int) q.getSingleResult();
        return contador;
    }

}
