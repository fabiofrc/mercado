package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.Pedido;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PedidoFacade extends DaoGeneric<Pedido> implements Serializable {

    public PedidoFacade() {
        super(Pedido.class);
    }

    EntityManager em = new JpaUtil().createEntityManager();

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

}
