package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.Pedido;
import java.io.Serializable;
import javax.persistence.EntityManager;

public class PedidoFacade extends DaoGeneric<Pedido> implements Serializable {

    public PedidoFacade() {
        super(Pedido.class);
    }

    EntityManager entityManager = new JpaUtil().createEntityManager();

    public Pedido porId(Long id) {
        return entityManager.find(Pedido.class, id);
    }

}
