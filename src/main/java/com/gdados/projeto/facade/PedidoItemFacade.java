package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.PedidoItem;
import java.io.Serializable;
import javax.persistence.EntityManager;

public class PedidoItemFacade extends DaoGeneric<PedidoItem> implements Serializable {

    public PedidoItemFacade() {
        super(PedidoItem.class);
    }
   
    EntityManager entityManager = new JpaUtil().createEntityManager();

}
