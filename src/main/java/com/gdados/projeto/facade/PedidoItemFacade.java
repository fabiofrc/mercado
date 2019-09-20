package com.gdados.projeto.facade;

import com.gdados.projeto.model.PedidoItem;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PedidoItemFacade implements Serializable {
 private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public PedidoItem save(PedidoItem entity) {
        em.persist(entity);
        return entity;
    }

    public PedidoItem update(PedidoItem entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(PedidoItem entity) {
        em.remove(entity);
    }

    public List<PedidoItem> getAll() {
        Query q = em.createQuery("SELECT p FROM PedidoItem p");
        return q.getResultList();
    }

    public PedidoItem getById(Long id) {
        Query q = em.createQuery("SELECT c FROM PedidoItem c WHERE c.id = :id");
        q.setParameter("id", id);
        return (PedidoItem) q.getSingleResult();
    }

    public List<PedidoItem> listaPedidoItemByPedido(Long id) {
        try {
            @SuppressWarnings("JPQLValidation")
            Query q = em.createQuery("SELECT i FROM PedidoItem i JOIN i.pedido p Where p.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }
}
