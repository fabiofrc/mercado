package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.PedidoItem;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PedidoItemFacade extends DaoGeneric<PedidoItem> implements Serializable {

    public PedidoItemFacade() {
        super(PedidoItem.class);
    }

    EntityManager em = new JpaUtil().createEntityManager();

    public List<PedidoItem> listaPedidoItemByPedido(Long id) {
        try {
            Query q = em.createQuery("SELECT i FROM PedidoItem i JOIN i.pedido p Where p.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }
}
